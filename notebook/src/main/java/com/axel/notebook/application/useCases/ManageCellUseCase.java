package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.CellResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.*;
import com.axel.notebook.application.services.IManageCellUseCase;
import com.axel.notebook.application.services.IManageTableUseCase;
import com.axel.notebook.application.services.producers.ICellProducer;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.services.CellService;
import com.axel.notebook.domain.valueObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManageCellUseCase implements IManageCellUseCase {
    //Dependency injection
    private final IManageTableUseCase manageTableUseCase;
    private final ICellProducer cellProducer;
    private final ITableRepository tableRepository;
    private final ICellRepository cellRepository;
    private final ITaskCellRepository taskCellRepository;
    private final IStudentCellRepository studentCellRepository;
    private final INoteCellRepository noteCellRepository;
    private final CellService cellService;

    //Constructor
    @Autowired
    public ManageCellUseCase(final IManageTableUseCase manageTableUseCase,
                             final ICellProducer cellProducer,
                             final ITableRepository tableRepository,
                             final ICellRepository cellRepository,
                             final ITaskCellRepository taskCellRepository,
                             final IStudentCellRepository studentCellRepository,
                             final INoteCellRepository noteCellRepository) {
        this.manageTableUseCase = manageTableUseCase;
        this.cellProducer = cellProducer;
        this.tableRepository = tableRepository;
        this.cellRepository = cellRepository;
        this.taskCellRepository = taskCellRepository;
        this.studentCellRepository = studentCellRepository;
        this.cellService = new CellService();
        this.noteCellRepository = noteCellRepository;
    }

    //get all cells from table
    public CellResponse getCellsFromTableUseCase(String token, String nameSubject, String nameYear, String nameCourse,
                                                 String nameGroup, String nameTable){
        //check data
        if(token == null || token.isEmpty() || nameSubject == null || nameSubject.isEmpty() ||
                nameYear == null || nameYear.isEmpty() || nameCourse == null || nameCourse.isEmpty() ||
                nameGroup == null || nameGroup.isEmpty() || nameTable == null || nameTable.isEmpty()){
            throw new ApplicationException("Existe algún dato que falta para recuperar las celdas de la tabla.");
        }

        //decode token and get data
        Map<String,String> dataToken = getProfileData(token);
        String idTeacherString = dataToken.get("idProfile");

        if(idTeacherString == null || idTeacherString.isEmpty()){
            throw new ApplicationException("No existe información para este usuario.");
        }

        //convert string to int
        int idTeacher = Integer.parseInt(idTeacherString);

        if(idTeacher <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        //Table in list rows format
        List<Row> rows = new ArrayList<>();

        //initialize response with empty results
        CellResponse responseTable = new CellResponse(rows);

        try{
            //get idGroup
            int idGroup = manageTableUseCase.getIdGroup(idTeacher, nameGroup, nameCourse, nameSubject,nameYear);
            if(idGroup <= 0){
                throw new ApplicationException("No existe el grupo en el sistema.");
            }

            //get table
            Table table = tableRepository.findTable(idTeacher, idGroup, nameTable);

            if(table == null){
                throw new ApplicationException("No existe la tabla en el sistema.");
            }

            //get headers task
            Row rowTasks = getTasksFromTable(table);

            //if exists tasks created, then create headers task
            if(rowTasks != null){
                //add headers task into response table
                rows.add(rowTasks);
            }

            //create rows for students, set students
            List<Row> rowStudents = getStudentsTable(table);

            //if table not contains students return table empty or with header tasks
            if(rowStudents.isEmpty()){
                responseTable.setTableCells(rows);
                return responseTable;
            }

            //insert notes into rows for students
            List<Row> allRows = getNotesFromTableForStudents(table, rowStudents, rowTasks);

            //insert rows with students and notes into table response
            rows.addAll(allRows);
        }
        catch(ApplicationException e){
            throw new ApplicationException(e.getMessage());
        }

        //returns all rows from table completed with format response
        responseTable.setTableCells(rows);
        return responseTable;
    }

    //petition to User microservice to recollect data profile
    public Map<String, String> getProfileData(String token) {
        return cellProducer.sendToken(token);
    }

    //get all tasks from table to construct header row for tasks
    private Row getTasksFromTable(Table table){
        List<Cell> tasks = new ArrayList<>();

        if(table == null){
            throw new ApplicationException("No existe la tabla en el sistema.");
        }

        //get cells
        int idTable = table.getIdTable();
        List<Object[]> cells = cellRepository.getCellsForIdTableAndType(idTable, "TASK");

        //if class not contains tasks
        if(cells == null || cells.isEmpty()){
            return new Row(null);
        }

        //iteration for cells
        for(Object[] taskCell : cells){
            //get task to cell
            int idCell = (int)taskCell[0];
            if(idCell <= 0){
                throw new ApplicationException("No existe la celda en el sistema.");
            }
            //get task from DB
            Task task = taskCellRepository.getTaskByIdCell(idCell);
            //construct cell for table
            Cell cellTask = cellService.addCellTaskOrStudent(task.getNameTask(), task.getPositionRow(), task.getPositionCol(), task.getIdTask());
            if(cellTask == null){
                throw new ApplicationException("Ha habido un problema recuperando la tarea.");
            }
            tasks.add(cellTask);
        }
        //add list to tasks into row
        return new Row(tasks);
    }

    private List<Row> getStudentsTable(Table table){
        //row with students
        List<Row> rows = new ArrayList<>();

        if(table == null){
            throw new ApplicationException("No existe la tabla en el sistema.");
        }

        //get cells
        int idTable = table.getIdTable();
        List<Object[]> cells = cellRepository.getCellsForIdTableAndType(idTable, "STUDENT");

        //if the table not contains students, return empty rows
        if(cells == null || cells.isEmpty()){
            return rows;
        }

        //iteration for cells
        for(Object[] studentCell : cells){
            //get idCell for Student from CellEntity table
            int idCellStudent = (int)studentCell[0];

            if(idCellStudent <= 0){
                throw new ApplicationException("No existe la celda en el sistema.");
            }

            //get idProfile for studentCellEntity
            int idProfileStudent = studentCellRepository.getIdStudentByIdCell(idCellStudent);

            if(idProfileStudent <= 0){
                throw new ApplicationException("Error al recuperar el identificador del perfil del estudiante.");
            }

            //get profile data
            Map<String, String> profileStudent = cellProducer.sendIdProfile(idProfileStudent);

            if(profileStudent == null){
                throw new ApplicationException("Problema con el perfil del estudiante, no hay datos.");
            }

            String name = profileStudent.get("name");
            String surname1 = profileStudent.get("surname1");
            String surname2 = profileStudent.get("surname2");
            String completeName = name + " " + surname1 + " " + surname2;

            //get positions with an array
            int [] positions = new int[2];
            //row position
            positions = cellRepository.getPositionsByIdCell(idCellStudent);
            //creates cell student
            Cell cellStudent = cellService.addCellTaskOrStudent(completeName, positions[0], positions[1], idCellStudent);

            if(cellStudent == null){
                throw new ApplicationException("Problema al crear la celda del estudiante.");
            }

            //creates row student without notes
            Row rowStudent = cellService.addRowStudent(cellStudent);

            //insert row into list
            rows.add(rowStudent);
        }
        //returns list with rows
        return rows;
    }

    private List<Row> getNotesFromTableForStudents(Table table, List<Row> students, Row rowTasks){
        List<Row> rows = new ArrayList<>();

        //check data
        if(students == null || students.isEmpty()){
            throw new ApplicationException("Error con la lista de estudiantes.");
        }

        if(table == null){
            throw new ApplicationException("No existe la tabla en el sistema.");
        }

        //get cells
        int idTable = table.getIdTable();
        List<Object[]> cells = cellRepository.getCellsForIdTableAndType(idTable, "NOTE");


        //if into task headers not exist tasks, return students
        if(rowTasks == null || rowTasks.getRowNotebook() == null || rowTasks.getRowNotebook().isEmpty()){
            return students;
        }

        //if into table not contains notes return empty notes table
        if(cells == null || cells.isEmpty()){
            rows.add(rowTasks);
            rows.addAll(students);
            return rows;
        }

        List<Note> notes = new ArrayList<>();

        //extract for one cellEntity, get NoteCellEntity
        for(Object[] studentCell : cells){
            Note noteCell = noteCellRepository.getNoteForId((int)studentCell[0]);

            if(noteCell == null){
                throw new ApplicationException("No existe la nota asociada a la celda.");
            }
            notes.add(noteCell);
        }

        //iteration for all students into table
        for(Row studentRow : students){
            List<Cell> notesStudent = new ArrayList<>();
            //insert student into row
            notesStudent.add(studentRow.getRowNotebook().get(0));
            //iteration for task for one student
            for(Cell task : rowTasks.getRowNotebook()){
                //iteration for one note for one task for one student
                for(Note note : notes){
                    if(note.getIdTask() == task.getIdCell() && note.getIdStudent() == studentRow.getRowNotebook().get(0).getIdCell()){
                        //create cell note
                        Cell cellNote = cellService.addCellNote(note.getNote(), note.getPositionRow(),note.getPositionCol(), note.getIdNote());
                        //insert note into row student
                        notesStudent.add(cellNote);
                    }
                }
            }
            //insert notes into row student
            studentRow.setRowNotebook(notesStudent);
            //insert row into table
            rows.add(studentRow);
        }
        return rows;
    }
}

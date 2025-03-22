package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.CellResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ICellRepository;
import com.axel.notebook.application.repositories.IStudentCellRepository;
import com.axel.notebook.application.repositories.ITableRepository;
import com.axel.notebook.application.repositories.ITaskCellRepository;
import com.axel.notebook.application.services.IManageCellUseCase;
import com.axel.notebook.application.services.IManageTableUseCase;
import com.axel.notebook.application.services.producers.ICellProducer;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.services.CellService;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.domain.valueObjects.RowNotebook;
import com.axel.notebook.domain.valueObjects.Student;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
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
    private final JpaNoteCellRepository jpaNoteCellRepository;
    private final CellService cellService;

    //Constructor
    @Autowired
    public ManageCellUseCase(final IManageTableUseCase manageTableUseCase,
                             final ICellProducer cellProducer,
                             final ITableRepository tableRepository,
                             final ICellRepository cellRepository,
                             final ITaskCellRepository taskCellRepository,
                             final IStudentCellRepository studentCellRepository, JpaNoteCellRepository jpaNoteCellRepository) {
        this.manageTableUseCase = manageTableUseCase;
        this.cellProducer = cellProducer;
        this.tableRepository = tableRepository;
        this.cellRepository = cellRepository;
        this.taskCellRepository = taskCellRepository;
        this.studentCellRepository = studentCellRepository;
        this.jpaNoteCellRepository = jpaNoteCellRepository;
        this.cellService = new CellService();
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
        int idTeacher = 0;

        if(idTeacherString == null || idTeacherString.isEmpty() ||
                dataToken.get("role") == null || dataToken.get("role").isEmpty()){
            throw new ApplicationException("No existe información para este usuario.");
        }

        idTeacher = Integer.parseInt(idTeacherString);

        if(idTeacher <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        //Table in list rows format
        List<RowNotebook> rows = new ArrayList<>();

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
            RowNotebook rowTasks = getTasksFromTable(table);

            //if exists tasks created, then create headers task
            if(rowTasks != null){
                //add headers task into response table
                rows.add(rowTasks);
            }

            //create rows for students, set students
            List<RowNotebook> rowStudents = getStudentsTable(table);

            //if table not contains students return table empty or with header tasks
            if(rowStudents == null || rowStudents.isEmpty()){
                responseTable.setTableCells(rows);
                return responseTable;
            }

            //insert notes into rows for students
            List<RowNotebook> rowsWithNotes = getNotesFromTableForStudents(table, rowStudents, rowTasks);

            //insert rows with students and notes into table response
            rows.addAll(rowsWithNotes);
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
    private RowNotebook getTasksFromTable(Table table){
        List<Task> tasks = new ArrayList<>();

        if(table == null){
            throw new ApplicationException("No existe la tabla en el sistema.");
        }

        //get cells
        int idTable = table.getIdTable();
        List<Object[]> cells = cellRepository.getCellsForIdTableAndType(idTable, "TASK");

        //if class not contains tasks
        if(cells == null || cells.isEmpty()){
            return new RowNotebook(null,null,null);
        }

        //iteration for cells
        for(Object[] taskCell : cells){
            //get task to cell
            int idCell = (int)taskCell[0];
            if(idCell <= 0){
                throw new ApplicationException("No existe la celda en el sistema.");
            }

            Task task = taskCellRepository.getNameByIdCell(idCell);
            if(task == null){
                throw new ApplicationException("Ha habido un problema recuperando la tarea.");
            }
            tasks.add(task);
        }
        //add list to tasks into row
        return new RowNotebook(null,null, tasks);
    }

    private List<RowNotebook> getStudentsTable(Table table){
        //row with students
        List<RowNotebook> rows = new ArrayList<>();

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

            //creates row
            Student student = cellService.addStudent(idProfileStudent, name, surname1, surname2, idCellStudent);
            RowNotebook rowStudent = new RowNotebook(student, null, null);

            if(student == null){
                throw new ApplicationException("Problema al crear el estudiante con el perfil.");
            }

            //insert row into list
            rows.add(rowStudent);
        }
        //returns list with rows
        return rows;
    }

    private List<RowNotebook> getNotesFromTableForStudents(Table table, List<RowNotebook> students, RowNotebook rowTasks){
        List<RowNotebook> studentsWithNotes = new ArrayList<>();

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

        //if table not contains columns and notes return empty notes
        if(cells == null || cells.isEmpty()){
            return students;
        }

        //if not exist tasks, not exist notes and return empty list
        if(rowTasks == null || rowTasks.getTasks() == null || rowTasks.getTasks().isEmpty()){
            return null;
        }

        List<NoteCellEntity> notes = new ArrayList<>();

        //extract for one cellEntity, get NoteCellEntity
        for(Object[] studentCell : cells){
            NoteCellEntity noteCell = jpaNoteCellRepository.findByIdCell((int)studentCell[0]);

            if(noteCell == null){
                throw new ApplicationException("No existe la nota asociada a la celda.");
            }
            notes.add(noteCell);
        }

        //iteration for all students into table
        for(RowNotebook studentRow : students){
            List<Note> notesStudent = new ArrayList<>();
            //iteration for task for one student
            for(Task task : rowTasks.getTasks()){
                //iteration for one note for one task for one student
                for(NoteCellEntity note : notes){
                    if(note.getTaskCell().getIdCell() == task.getIdTask() &&
                            note.getStudentCell().getIdCell() == studentRow.getStudent().getIdCellStudent()){
                        Note newNote = new Note(note.getIdCell(), note.getNote());
                        notesStudent.add(newNote);
                    }
                }
            }
            //insert notes into row student
            studentRow.setNotes(notesStudent);
            //insert row into table
            studentsWithNotes.add(studentRow);
        }
        return studentsWithNotes;
    }
}

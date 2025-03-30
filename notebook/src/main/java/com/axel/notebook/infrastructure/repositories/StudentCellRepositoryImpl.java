package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IStudentCellRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaStudentCellRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import java.util.Comparator;
import java.util.List;

@Repository
public class StudentCellRepositoryImpl implements IStudentCellRepository {
    //Dependency injection
    private final JpaStudentCellRepository jpaStudentCellRepository;
    private final JpaCellRepository jpaCellRepository;
    private final NoteCellRepositoryImpl noteCellRepository;

    //Constructor
    public StudentCellRepositoryImpl(JpaStudentCellRepository jpaStudentCellRepository,
                                     JpaCellRepository jpaCellRepository,
                                     @Lazy NoteCellRepositoryImpl noteCellRepository) {
        this.jpaStudentCellRepository = jpaStudentCellRepository;
        this.jpaCellRepository = jpaCellRepository;
        this.noteCellRepository = noteCellRepository;
    }

    public int getIdStudentByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de celda no existe o es nulo.");
        }

        StudentCellEntity student = jpaStudentCellRepository.findStudentCellEntityById(idCell);

        if(student == null){
            throw new InfrastructureException("En la celda no existe ningun estudiante.");
        }
        return student.getIdProfile();
    }

    public StudentCellEntity getStudentByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de celda no existe o es nulo.");
        }

        StudentCellEntity student = jpaStudentCellRepository.findStudentCellEntityById(idCell);

        if(student == null){
            throw new InfrastructureException("En la celda no existe ningun estudiante.");
        }
        return student;
    }

    public int getIdCellByIdStudent(int idStudent, int idTable){
        if(idStudent <= 0 || idTable <= 0){
            throw new InfrastructureException("El identificador del perfil del estudiante no es correcto.");
        }

        List<StudentCellEntity> students = jpaStudentCellRepository.findStudentCellEntityByIdProfile(idStudent);
        if(students == null){
            throw new InfrastructureException("No se ha encontrado el estudiante en la base de datos.");
        }
        for(StudentCellEntity student : students){
            //student.getIdCell() -  idTable
            List<Integer> studentsIntoTable = jpaCellRepository.findByTableIdAndStudentType(idTable);
            if(studentsIntoTable == null){
                throw new InfrastructureException("Error buscando el estudiante en la base de datos.");
            }

            for(Integer idCellStudent : studentsIntoTable){
                if(idCellStudent == student.getIdCell()){
                    return student.getIdCell();
                }
            }
        }
        return -1;
    }

    public boolean deleteStudentRowIntoTable(Table table, int idCellStudent){
        boolean isDeleted = false;
        int positionRowStudent = 0;
        if(table == null || idCellStudent <= 0){
            throw new InfrastructureException("Error, la tabla no existe en el sistema o el perfil del estudiante es erroneo.");
        }

        //get position row student (tengo el idCell)
        positionRowStudent = jpaCellRepository.findPositionRowByIdCell(idCellStudent);
        if(positionRowStudent <= 0){
            throw new InfrastructureException("No se ha encontrado la posición de la fila del estudiante.");
        }

        //get all notes for this table and position row and delete notes
        List<Integer> idNotes = jpaCellRepository.findForTypeIdAndRow(table.getIdTable(), positionRowStudent, "NOTE");
        if(!idNotes.isEmpty()) {
            //delete all notes into noteCellEntity for this column and task
            for (Integer idNote : idNotes) {
                isDeleted = noteCellRepository.deleteNote(idNote);
                if (!isDeleted) {
                    throw new InfrastructureException("Error al eliminar la nota en el sistema.");
                }
            }
        }

        //get task cell for table and positionColumnTask
        List<Integer> idCellTask = jpaCellRepository.findForTypeIdAndRow(table.getIdTable(), positionRowStudent, "STUDENT");
        if(idCellTask.size() != 1) {
            throw new InfrastructureException("No existe la tarea a borrar.");
        }
        //delete task into taskCellEntity for this idCell
        for (Integer idCell : idCellTask) {
            isDeleted = deleteStudent(idCell);
            if (!isDeleted) {
                throw new InfrastructureException("Error al eliminar la tarea en el sistema.");
            }
        }

        //reallocate columns
        boolean isAllocated = allocateRows(table.getIdTable(), positionRowStudent);
        if(!isAllocated){
            throw new InfrastructureException("Error, no se han recolocado correctamente las celdas de la tabla.");
        }
        return isDeleted;
    }

    public boolean deleteStudent(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la tararea no es correcto.");
        }
        //delete task into taskCellEntity
        jpaStudentCellRepository.deleteByIdCell(idCell);
        //check if is deleted correctly if not deleted correctly return false else true
        return jpaStudentCellRepository.findByIdCell(idCell) == null;
    }

    public boolean allocateRows(int idTable, int positionRowStudent){
        List<Object[]> rowsStudent = jpaCellRepository.getAllByIdAndType(idTable, "STUDENT");
        List<Object[]> rowsNote = jpaCellRepository.getAllByIdAndType(idTable, "NOTE");
        //exist rows and is necessary allocate
        if(!rowsStudent.isEmpty()){
            //sort students for row, minus row to max row
            rowsStudent.sort(Comparator.comparingInt(a -> (int) a[2]));

            for(Object[] row : rowsStudent){
                int idCell = (int)row[0];
                int positionRow = (int)row[2];
                if(positionRow > positionRowStudent){
                    //update to new position
                    jpaCellRepository.setPositionRow(idCell, positionRow-1);
                }
            }

            //sort notes for column, minus col to max col
            rowsNote.sort(Comparator.comparingInt(a -> (int) a[2]));

            //exist notes
            if(!rowsNote.isEmpty()){
                for(Object[] row : rowsNote){
                    int idCell = (int)row[0];
                    int positionRow = (int)row[2];
                    if(positionRow > positionRowStudent){
                        //update to new position
                        jpaCellRepository.setPositionRow(idCell, positionRow-1);
                    }
                }
            }
        }
        return true;
    }
}

package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ITableRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.infrastructure.JpaEntities.*;
import com.axel.notebook.infrastructure.adapters.TableAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TableRepositoryImpl implements ITableRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;
    private final TableAdapterInfrastructure tableAdapter;
    private final TableAdapterInfrastructure tableAdapterInfrastructure;
    private final JpaSubjectRepository jpaSubjectRepository;
    private final JpaYearRepository jpaYearRepository;
    private final JpaCourseRepository jpaCourseRepository;
    private final JpaGroupRepository jpaGroupRepository;

    //Constructor
    public TableRepositoryImpl(JpaTableRepository jpaTableRepository, TableAdapterInfrastructure tableAdapter, TableAdapterInfrastructure tableAdapterInfrastructure, JpaSubjectRepository jpaSubjectRepository, JpaYearRepository jpaYearRepository, JpaCourseRepository jpaCourseRepository, JpaGroupRepository jpaGroupRepository) {
        this.jpaTableRepository = jpaTableRepository;
        this.tableAdapter = tableAdapter;
        this.tableAdapterInfrastructure = tableAdapterInfrastructure;
        this.jpaSubjectRepository = jpaSubjectRepository;
        this.jpaYearRepository = jpaYearRepository;
        this.jpaCourseRepository = jpaCourseRepository;
        this.jpaGroupRepository = jpaGroupRepository;
    }

    public List<String> getAllTablesForNameSubject(int idProfile, int idGroup){
        List<String> tables = new ArrayList<>();

        if(idProfile <= 0 || idGroup <= 0){
            throw new InfrastructureException("Error con el prefil de usuario o el grupo");
        }

        List<TableEntity> tablesEntities = jpaTableRepository.findAllTablesByIdGroup(idGroup);

        if(tablesEntities != null){
            for(TableEntity tableEntity : tablesEntities){
                tables.add(tableEntity.getNameTable());
            }
        }
        return tables;
    }

    public Table createTable(Table table){
        if(table == null){
            throw new InfrastructureException("La tabla para guardar es nula o vacía.");
        }
        TableEntity tableEntity = tableAdapter.fromApplicationWithoutId(table);

        if(jpaTableRepository.exists(table.getIdTeacher(), table.getNameTable(), table.getIdGroup())){
           throw new InfrastructureException("La tabla ya existe en el sistema.");
        }

        tableEntity = jpaTableRepository.save(tableEntity);
        return tableAdapter.toApplication(tableEntity);
    }

    public Table findTable(int idProfile, int idGroup, String nameTable){
        if(idProfile <= 0 || idGroup <= 0 || nameTable == null){
            throw new InfrastructureException("Error al intentar buscar la tabla, algún parametro no es correcto.");
        }

        TableEntity table = jpaTableRepository.findByProfileGroupName(idProfile, idGroup, nameTable);
        return tableAdapterInfrastructure.toApplication(table);
    }

    public boolean existTableWithClassCode(String classCode){
        if(classCode == null){
            throw new InfrastructureException("El codigo de la clase es nulo.");
        }

        TableEntity table = jpaTableRepository.findByClassCode(classCode);

        if(table == null || !Objects.equals(table.getClassCode(), classCode)){
            return false;
        }
        return true;
    }

    public Table findTableByClassCode(String classCode){
        if(classCode == null){
            throw new InfrastructureException("El codigo de la clase es nulo.");
        }

        TableEntity table = jpaTableRepository.findByClassCode(classCode);

        if(table == null || !Objects.equals(table.getClassCode(), classCode)){
            throw new InfrastructureException("Error al recuperar la tabla con el codigo de la clase.");
        }

        return tableAdapterInfrastructure.toApplication(table);
    }

    public boolean deleteTable(String classCode){
        if(classCode == null || !existTableWithClassCode(classCode)){
            throw new InfrastructureException("El codigo de la clase es nulo o la clase no existe");
        }

        boolean isDeleted = false;
        int idTable = jpaTableRepository.findByClassCode(classCode).getIdTable();
        if(idTable <= 0){
            throw new InfrastructureException("La tabla no existe");
        }

        jpaTableRepository.deleteById(idTable);
        if(!existTableWithClassCode(classCode)){
            isDeleted = true;
        }

        return isDeleted;
    }

    private boolean existTable(int idProfile, int idGroup, String nameTable){
        List<String> nameTables = getAllTablesForNameSubject(idProfile, idGroup);
        if(nameTables.contains(nameTable)){
            return true;
        }
        return false;
    }

    public int updateNameTable(int idProfile, String nameSubject, String nameYear, String nameCourse, String nameGroup, String nameTable, String newNameTable){
        if(idProfile <= 0 || nameSubject == null || nameSubject.isEmpty() ||
                nameYear == null || nameYear.isEmpty() ||nameGroup == null || nameGroup.isEmpty() ||
                newNameTable == null || newNameTable.isEmpty() || nameCourse == null || nameCourse.isEmpty() ||
                nameTable == null || nameTable.isEmpty()){
            throw new ApplicationException("Algún dato no es correcto para actualizar el grupo.");
        }

        SubjectEntity subject = jpaSubjectRepository.findByNameAndIdProfile(nameSubject, idProfile);
        if(subject == null){
            throw new ApplicationException("Error al recuperar la asignatura.");
        }

        YearEntity year = jpaYearRepository.findByNameAndIdProfile(nameYear, idProfile);
        if(year == null){
            throw new ApplicationException("Error al recuperar el año.");
        }

        CourseEntity course = jpaCourseRepository.findByYearSubjectName(year, nameCourse);
        if(course == null){
            throw new ApplicationException("Error al recuperar el curso.");
        }

        GroupEntity group = jpaGroupRepository.findByNameCourseSubject(nameGroup, course, subject);
        if(group == null){
            throw new ApplicationException("Error al recuperar el grupo.");
        }

        if(existTable(idProfile, group.getIdGroup(), newNameTable)){
            throw new InfrastructureException("El nombre de la tabla existe para este usuario y la selección.");
        }

        TableEntity table = jpaTableRepository.findByProfileGroupName(idProfile, group.getIdGroup(), nameTable);
        if(table == null){
            throw new ApplicationException("Error al recuperar la tabla.");
        }

        int isUploaded = jpaTableRepository.updateNameByIdTable(table.getIdTable(), newNameTable);

        if(isUploaded == 1){
            return group.getIdGroup();
        }
        return -1;
    }
}

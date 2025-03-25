package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Task;

public interface ITaskCellRepository{
    public Task getTaskByIdCell(int idCell);

    public Task addTask(Task task, Table table);
}

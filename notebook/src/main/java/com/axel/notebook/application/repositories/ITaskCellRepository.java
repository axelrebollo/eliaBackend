package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.Task;

public interface ITaskCellRepository{
    public Task getNameByIdCell(int idCell);
}

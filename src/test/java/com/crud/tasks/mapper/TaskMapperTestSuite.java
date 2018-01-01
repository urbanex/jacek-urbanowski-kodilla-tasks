package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {
    @Autowired
    TaskMapper taskMapper;

    @Test
    public void mapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        Task task = new Task(1L, "test_title", "test_content");
        //when
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //then
        assertEquals(mappedTask, task);
    }

    @Test
    public void mapToTaskDto() {
        //given
        Task task = new Task(1L, "test_title", "test_content");
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        //when
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //then
        assertEquals(mappedTaskDto, taskDto);
    }

    @Test
    public void mapToTaskDtoList() {
        //given
        Task task1 = new Task(1L, "test1", "test1");
        Task task2 = new Task(2L, "test2", "test2");
        TaskDto task1Dto = new TaskDto(1L, "test1", "test1");
        TaskDto task2Dto = new TaskDto(2L, "test2", "test2");
        List<Task> taskList = new ArrayList<>(Arrays.asList(task1, task2));
        List<TaskDto> taskDtoList = new ArrayList<>(Arrays.asList(task1Dto, task2Dto));
        //when
        List<TaskDto> mappedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //then
        assertEquals(mappedTaskDtoList, taskDtoList);
    }

}
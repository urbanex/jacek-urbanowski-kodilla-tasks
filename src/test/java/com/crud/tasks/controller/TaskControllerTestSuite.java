package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldGetTasks() throws Exception {
        //given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "test_title1", "test_content1"));
        tasks.add(new TaskDto(2L, "test_title2", "test_content2"));

        when(taskController.getTasks()).thenReturn(tasks);

        //when & then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test_title1")))
                .andExpect(jsonPath("$[0].content", is("test_content1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("test_title2")))
                .andExpect(jsonPath("$[1].content", is("test_content2")));
    }

/*    @Test
    public void shouldGetTask() throws Exception {
        //given
        TaskDto task = new TaskDto(1L, "test_title", "test_content");
        when(taskController.getTask(ArgumentMatchers.anyLong())).thenReturn(task);

        //when & then
        mockMvc.perform(get("/v1/task/getTask", "taskId", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_title")))
                .andExpect(jsonPath("$.content", is("test_content")));

    }*/

    @Test
    public void shouldGetTask() throws Exception {
        //given
        TaskDto task = new TaskDto(1L, "test_title", "test_content");
        when(taskController.getTask(ArgumentMatchers.anyLong())).thenReturn(task);

        //when & then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_title")))
                .andExpect(jsonPath("$.content", is("test_content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L, "test_task", "test_content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //when & then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));

        //then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test_task")))
                .andExpect(jsonPath("$[0].content", is("test_content")));
    }

/*    @Test
    public void updateTask() throws Exception {
    }

    @Test
    public void deleteTask() throws Exception {
    }*/

}
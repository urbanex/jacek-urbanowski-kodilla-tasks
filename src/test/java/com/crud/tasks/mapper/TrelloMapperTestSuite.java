package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToLists() {
        //given
        TrelloListDto trelloList1Dto = new TrelloListDto("1", "jeden", true);
        TrelloListDto trelloList2Dto = new TrelloListDto("2", "dwa", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>(Arrays.asList(trelloList1Dto, trelloList2Dto));

        //when
        List<TrelloList> trelloLists = trelloMapper.mapToLists(trelloListDtoList);

        //then
        assertEquals(2, trelloLists.size());
        assertTrue(trelloLists.containsAll(Arrays.asList(new TrelloList("1", "jeden", true),
                new TrelloList("2", "dwa", false))));

    }

/*
    @Test
    public void mapToListsDto() {
    }

    @Test
    public void mapToBoards() {
    }

    @Test
    public void mapToBoardsDto() {
    }

    @Test
    public void mapToCardDto() {
    }

    @Test
    public void mapToCard() {
    }
*/

}
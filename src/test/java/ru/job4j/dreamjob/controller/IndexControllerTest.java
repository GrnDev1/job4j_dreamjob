package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {
    private IndexController indexController = new IndexController();

    @Test
    public void whenRequestIndexPageThenGetHomePage() {
        assertThat(indexController.getIndex()).isEqualTo("index");
    }

}
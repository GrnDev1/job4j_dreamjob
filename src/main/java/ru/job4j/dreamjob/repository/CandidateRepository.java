package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

public interface CandidateRepository {
    Candidate save(Candidate candidate);

    void deleteById(int id);

    boolean update(Candidate candidate);

    Optional<Candidate> findById(int id);

    Collection<Candidate> findAll();

}
/**
 * Задание
 * <p>
 * 1. Создать модель данных ru.job4j.dreamjob.model.Candidate.java.
 * В модели добавить поля: id, name (имя кандидата), description (описание), creationDate (дата создания);
 * <p>
 * 2. Создать репозиторий ru.job4j.dreamjob.repository.CandidateRepository и
 * реализацию ru.job4j.dreamjob.repository.MemoryCandidateRepository.
 * В конструкторе реализации создать несколько кандидатов и пометить ее аннотацией @Repository;
 * <p>
 * 3. Создать контроллер ru.job4j.dreamjob.controller.CandidateController и
 * передать данные в представление через Model.
 * Не забыть указать аннотации @Controller и @RequestMapping("/candidates");
 * <p>
 * 4. Создать вид src/main/resources/templates/candidates/list.html.
 * Отобразить в виде таблицы всех кандидатов с помощью цикла Thymeleaf;
 */
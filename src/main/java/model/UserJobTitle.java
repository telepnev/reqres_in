package model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Игнорируем NULL при создании обьекта (конструктор без одно поля и в нем автоматом будет ноль). Включаем только параметры которые NON_NULL)
public class UserJobTitle {
    private String name;
    private String job;

    public UserJobTitle(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public UserJobTitle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

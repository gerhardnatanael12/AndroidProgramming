package com.example.tasklist;

public class listTask {
    private String taskNote;
    private String tanggalPengumpulan;

    public listTask(String taskNote, String tanggalPengumpulan) {
        this.taskNote = taskNote;
        this.tanggalPengumpulan = tanggalPengumpulan;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public String getTanggalPengumpulan() {
        return tanggalPengumpulan;
    }

    public void setTanggalPengumpulan(String tanggalPengumpulan) {
        this.tanggalPengumpulan = tanggalPengumpulan;
    }
}

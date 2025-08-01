package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoInscripcion estado;

    public Inscripcion() {
    }

    public Inscripcion(Curso curso, Estudiante estudiante, LocalDate fechaInscripcion, EstadoInscripcion estado) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", curso=" + curso.getNombre() +
                ", estudiante=" + estudiante.getNombre() +
                ", fechaInscripcion=" + fechaInscripcion +
                ", estado=" + estado +
                '}';
    }
}

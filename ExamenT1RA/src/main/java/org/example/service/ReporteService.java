package org.example.service;

import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.time.LocalDate;

import java.util.List;

public class ReporteService {

    private final Session session;

    public ReporteService(Session session) {
        this.session = session;
    }


    public void reporteCursosConEstadisticas() {
        String hql = """
            SELECT c.nombre, COUNT(i.id), AVG(c.creditos)
            FROM Curso c
            JOIN c.inscripciones i
            GROUP BY c.id
            HAVING COUNT(i.id) >= 2
        """;
        List<Object[]> resultados = session.createQuery(hql, Object[].class).getResultList();
        System.out.println("\nCursos con número de inscripciones y promedio de créditos:");
        resultados.forEach(r ->
                System.out.printf("Curso: %s | Inscripciones: %s | Promedio Créditos: %.2f%n",
                        r[0], r[1], r[2]));
    }

    public void estudiantesConMasDeDosCursos() {
        String hql = """
            SELECT e.nombre, e.email, COUNT(i.id)
            FROM Estudiante e
            JOIN e.inscripciones i
            GROUP BY e.id
            HAVING COUNT(DISTINCT i.curso.id) > 2
        """;
        List<Object[]> resultados = session.createQuery(hql, Object[].class).getResultList();
        System.out.println("\nEstudiantes con más de 2 cursos:");
        resultados.forEach(r ->
                System.out.printf("Nombre: %s | Email: %s | Total Cursos: %s%n",
                        r[0], r[1], r[2]));
    }

    // Consulta c) Filtros dinámicos
    public void buscarInscripciones(LocalDate desde, LocalDate hasta, EstadoInscripcion estado,
                                    String nombreEstudiante, String codigoCurso) {
        StringBuilder hql = new StringBuilder("""
            SELECT i FROM Inscripcion i
            WHERE i.fechaInscripcion BETWEEN :desde AND :hasta
        """);
        if (estado != null) hql.append(" AND i.estado = :estado");
        if (nombreEstudiante != null) hql.append(" AND i.estudiante.nombre LIKE :nombreEstudiante");
        if (codigoCurso != null) hql.append(" AND i.curso.codigo LIKE :codigoCurso");
        hql.append(" ORDER BY i.fechaInscripcion DESC");

        Query<Inscripcion> query = session.createQuery(hql.toString(), Inscripcion.class);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        if (estado != null) query.setParameter("estado", estado);
        if (nombreEstudiante != null) query.setParameter("nombreEstudiante", "%" + nombreEstudiante + "%");
        if (codigoCurso != null) query.setParameter("codigoCurso", "%" + codigoCurso + "%");

        List<Inscripcion> resultados = query.getResultList();
        System.out.println("\nInscripciones filtradas:");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            resultados.forEach(System.out::println);
        }
    }


    public void cargaAcademicaProfesores() {
        String hql = """
            SELECT p.nombre, SUM(c.creditos)
            FROM Profesor p
            JOIN p.cursos c
            GROUP BY p.id
        """;
        List<Object[]> resultados = session.createQuery(hql, Object[].class).getResultList();
        System.out.println("\nCarga académica por profesor:");
        resultados.forEach(r ->
                System.out.printf("Profesor: %s | Total Créditos: %s%n", r[0], r[1]));
    }
}

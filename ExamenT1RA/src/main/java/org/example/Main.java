package org.example;

import org.example.entity.*;
import org.example.service.ReporteService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

            try (Session session = sessionFactory.openSession()) {
                Scanner scanner = new Scanner(System.in);
                ReporteService reporteService = new ReporteService(session);

                // Registro de prueba de Profesor
                session.beginTransaction();
                Profesor nuevoProfesor = new Profesor("Dr. Roberto Jiménez", "Biología");
                session.persist(nuevoProfesor);
                session.getTransaction().commit();

                // Mostrar todas las entidades
                mostrarEntidades(session);

                // Menú de reportes
                boolean salir = false;
                while (!salir) {
                    System.out.println("""
                        
                        --- MENÚ DE REPORTES ---
                        1. Cursos con estadísticas 
                        2. Estudiantes con más de 2 cursos 
                        3. Buscar inscripciones 
                        4. Carga académica por profesor 
                        0. Salir
                        -------------------------
                        Seleccione una opción:
                        """);
                    String opcion = scanner.nextLine();

                    session.beginTransaction();
                    switch (opcion) {
                        case "1" -> reporteService.reporteCursosConEstadisticas();
                        case "2" -> reporteService.estudiantesConMasDeDosCursos();
                        case "3" -> {
                            System.out.print("Desde (YYYY-MM-DD): ");
                            LocalDate desde = LocalDate.parse(scanner.nextLine());
                            System.out.print("Hasta (YYYY-MM-DD): ");
                            LocalDate hasta = LocalDate.parse(scanner.nextLine());
                            System.out.print("Estado (ACTIVA, FINALIZADA, RETIRADA): ");
                            EstadoInscripcion estado = EstadoInscripcion.valueOf(scanner.nextLine().toUpperCase());
                            System.out.print("Nombre estudiante (opcional): ");
                            String nombreEstudiante = scanner.nextLine();
                            System.out.print("Código curso (opcional): ");
                            String codigoCurso = scanner.nextLine();

                            reporteService.buscarInscripciones(
                                    desde,
                                    hasta,
                                    estado,
                                    nombreEstudiante.isBlank() ? null : nombreEstudiante,
                                    codigoCurso.isBlank() ? null : codigoCurso
                            );
                        }
                        case "4" -> reporteService.cargaAcademicaProfesores();
                        case "0" -> {
                            salir = true;
                            System.out.println("Saliendo...");
                        }
                        default -> System.out.println("Opción inválida.");
                    }
                    session.getTransaction().commit();
                }
            }

            sessionFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarEntidades(Session session) {
        session.beginTransaction();
        List<Profesor> profesores = session.createQuery("FROM Profesor", Profesor.class).list();
        System.out.println("\n📚 Lista de Profesores:");
        profesores.forEach(System.out::println);
        session.getTransaction().commit();

        session.beginTransaction();
        List<Estudiante> estudiantes = session.createQuery("FROM Estudiante", Estudiante.class).list();
        System.out.println("\n🎓 Lista de Estudiantes:");
        estudiantes.forEach(System.out::println);
        session.getTransaction().commit();

        session.beginTransaction();
        List<Curso> cursos = session.createQuery("FROM Curso", Curso.class).list();
        System.out.println("\n📘 Lista de Cursos:");
        cursos.forEach(System.out::println);
        session.getTransaction().commit();

        session.beginTransaction();
        List<Inscripcion> inscripciones = session.createQuery("FROM Inscripcion", Inscripcion.class).list();
        System.out.println("\n📝 Lista de Inscripciones:");
        inscripciones.forEach(System.out::println);
        session.getTransaction().commit();
    }
}





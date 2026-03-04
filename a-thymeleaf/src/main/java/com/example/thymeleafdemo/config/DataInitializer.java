package com.example.thymeleafdemo.config;

import com.example.thymeleafdemo.entity.*;
import com.example.thymeleafdemo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DataInitializer {

    @Bean
    @DependsOn("entityManagerFactory")
    CommandLineRunner initDatabase(
            InstituteRepository instituteRepository,
            DepartmentRepository departmentRepository,
            SpecialistRepository specialistRepository,
            GroupTypeRepository groupTypeRepository,
            GroupRepository groupRepository,
            StudentJpaRepository studentJpaRepository) {
        return args -> {
            // Check if data already exists
            if (instituteRepository.findAll().size() > 0) {
                System.out.println("Database already initialized, skipping...");
                return;
            }

            // Create Institutes
            Institute iit = new Institute("Institute of Information Technology", "Институт информационных технологий");
            Institute ime = new Institute("Institute of Mechanical Engineering", "Институт машиностроения");
            instituteRepository.save(iit);
            instituteRepository.save(ime);

            // Create Departments
            Department csDept = new Department("Computer Science", "Компьютерные науки");
            csDept.setInstitute(iit);
            departmentRepository.save(csDept);

            Department seDept = new Department("Software Engineering", "Программная инженерия");
            seDept.setInstitute(iit);
            departmentRepository.save(seDept);

            Department mechDept = new Department("Mechanical Design", "Машиностроительное проектирование");
            mechDept.setInstitute(ime);
            departmentRepository.save(mechDept);

            // Create Specialists
            Specialist csSpec = new Specialist("Computer Science", "Информатика и вычислительная техника");
            csSpec.setDepartment(csDept);
            specialistRepository.save(csSpec);

            Specialist seSpec = new Specialist("Software Engineering", "Программная инженерия");
            seSpec.setDepartment(seDept);
            specialistRepository.save(seSpec);

            Specialist mechSpec = new Specialist("Mechanical Engineering", "Машиностроение");
            mechSpec.setDepartment(mechDept);
            specialistRepository.save(mechSpec);

            // Create Group Types
            GroupType bachelor = new GroupType("Bachelor", "Бакалавриат");
            groupTypeRepository.save(bachelor);

            GroupType master = new GroupType("Master", "Магистратура");
            groupTypeRepository.save(master);

            // Create Groups
            Group cs101 = new Group("CS-101", "ИВТ-101", 1);
            cs101.setSpecialist(csSpec);
            cs101.setGroupType(bachelor);
            groupRepository.save(cs101);

            Group cs201 = new Group("CS-201", "ИВТ-201", 2);
            cs201.setSpecialist(csSpec);
            cs201.setGroupType(bachelor);
            groupRepository.save(cs201);

            Group se301 = new Group("SE-301", "ПИ-301", 3);
            se301.setSpecialist(seSpec);
            se301.setGroupType(bachelor);
            groupRepository.save(se301);

            Group mech101 = new Group("MECH-101", "МС-101", 1);
            mech101.setSpecialist(mechSpec);
            mech101.setGroupType(bachelor);
            groupRepository.save(mech101);

            // Create Students
            StudentJpa student1 = new StudentJpa("student001");
            student1.setGroup(cs101);
            studentJpaRepository.save(student1);

            StudentJpa student2 = new StudentJpa("student002");
            student2.setGroup(cs101);
            studentJpaRepository.save(student2);

            StudentJpa student3 = new StudentJpa("student003");
            student3.setGroup(se301);
            studentJpaRepository.save(student3);

            StudentJpa student4 = new StudentJpa("student004");
            student4.setGroup(mech101);
            studentJpaRepository.save(student4);

            System.out.println("Database initialized with demo data!");
        };
    }
}

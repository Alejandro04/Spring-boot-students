package persondemorest.persondemorest.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import persondemorest.persondemorest.EmailValidator;
import persondemorest.persondemorest.exception.ApiRequestException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class StudentService {
    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService,
                          EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }
    List<Student> getAllStudents() {
        return studentDataAccessService.selectAllStudents();
    }
    void addNewStudent(Student student) {
        UUID newStudentId = UUID.randomUUID();

        if (!emailValidator.test(student.getEmail())) {
            throw new ApiRequestException(student.getEmail() + " is not valid");
        }

        if (studentDataAccessService.isEmailTaken(student.getEmail())) {
            throw new ApiRequestException(student.getEmail() + " is taken");
        }

        studentDataAccessService.insertStudent(newStudentId, student);
    }

    List<StudentCourse> getAllCoursesForStudent(UUID studentId) {
        return studentDataAccessService.selectAllStudentCourses(studentId);
    }


}

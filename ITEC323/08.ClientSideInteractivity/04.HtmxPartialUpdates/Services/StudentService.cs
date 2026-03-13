using HtmxPartialUpdates.Models;

namespace HtmxPartialUpdates.Services;

/// <summary>
/// In-memory singleton store for student records.
/// Provides Create, Read, Update, and Delete operations
/// suitable for the HTMX CRUD table demonstration.
/// </summary>
public class StudentService
{
    private readonly List<Student> _students;
    private int _nextId = 4;
    private readonly object _lock = new();

    /// <summary>
    /// Initialises the service with three seed students so the table
    /// is not empty when the page first loads.
    /// </summary>
    public StudentService()
    {
        _students = new List<Student>
        {
            new() { Id = 1, Name = "Ava Thompson",  Course = "Web Development",   Grade = 91, IsActive = true  },
            new() { Id = 2, Name = "Noah Williams",  Course = "Data Structures",   Grade = 78, IsActive = true  },
            new() { Id = 3, Name = "Liam Martinez",  Course = "Cloud Computing",   Grade = 85, IsActive = false },
        };
    }

    /// <summary>Returns a snapshot of all student records, newest first.</summary>
    public IReadOnlyList<Student> GetAll()
    {
        lock (_lock)
        {
            return _students.AsReadOnly();
        }
    }

    /// <summary>Finds one student by id, or null if not found.</summary>
    public Student? GetById(int id)
    {
        lock (_lock)
        {
            return _students.FirstOrDefault(s => s.Id == id);
        }
    }

    /// <summary>Adds a student and assigns the next available id.</summary>
    public Student Add(string name, string course, int grade, bool isActive)
    {
        lock (_lock)
        {
            var student = new Student
            {
                Id       = _nextId++,
                Name     = name.Trim(),
                Course   = course.Trim(),
                Grade    = Math.Clamp(grade, 0, 100),
                IsActive = isActive,
            };
            _students.Insert(0, student);
            return student;
        }
    }

    /// <summary>
    /// Updates an existing student's fields.
    /// Returns the updated student, or null when the id is not found.
    /// </summary>
    public Student? Update(int id, string name, string course, int grade, bool isActive)
    {
        lock (_lock)
        {
            var student = _students.FirstOrDefault(s => s.Id == id);
            if (student is null) return null;

            student.Name     = name.Trim();
            student.Course   = course.Trim();
            student.Grade    = Math.Clamp(grade, 0, 100);
            student.IsActive = isActive;
            return student;
        }
    }

    /// <summary>
    /// Removes a student by id.
    /// Returns true when deleted, false when the id did not exist.
    /// </summary>
    public bool Delete(int id)
    {
        lock (_lock)
        {
            var student = _students.FirstOrDefault(s => s.Id == id);
            if (student is null) return false;
            _students.Remove(student);
            return true;
        }
    }
}

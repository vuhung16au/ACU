using HelloWorldData.Data;
using HelloWorldData.Models;
using Microsoft.EntityFrameworkCore;

/// <summary>
/// Hello World Data - A minimal EF Core + SQLite CRUD demonstration.
/// </summary>
/// 
// Ensure the database and schema exist.
using (var setupContext = new AppDbContext())
{
    setupContext.Database.Migrate();
}

Console.WriteLine("Starting CRUD demonstration with SQLite and EF Core...");

// CREATE: Add a new student record.
var student = new Student
{
    Name = "Alice"
};

using (var createContext = new AppDbContext())
{
    createContext.Students.Add(student);
    createContext.SaveChanges();
}

Console.WriteLine($"CREATE: Added Student with Id={student.Id}, Name={student.Name}");

// READ: Fetch the student we just inserted.
Student? studentFromDatabase;

using (var readContext = new AppDbContext())
{
    studentFromDatabase = readContext.Students.FirstOrDefault(s => s.Id == student.Id);
}

if (studentFromDatabase is null)
{
    Console.WriteLine("READ: Student not found.");
    return;
}

Console.WriteLine($"READ: Found Student with Id={studentFromDatabase.Id}, Name={studentFromDatabase.Name}");

// UPDATE: Change the student's name.
using (var updateContext = new AppDbContext())
{
    var studentToUpdate = updateContext.Students.First(s => s.Id == student.Id);
    studentToUpdate.Name = "Alice Updated";
    updateContext.SaveChanges();
}

Console.WriteLine("UPDATE: Student name changed to 'Alice Updated'.");

// READ AGAIN: Verify the update.
using (var verifyContext = new AppDbContext())
{
    var updatedStudent = verifyContext.Students.First(s => s.Id == student.Id);
    Console.WriteLine($"READ AFTER UPDATE: Id={updatedStudent.Id}, Name={updatedStudent.Name}");
}

// DELETE: Remove the student record.
using (var deleteContext = new AppDbContext())
{
    var studentToDelete = deleteContext.Students.First(s => s.Id == student.Id);
    deleteContext.Students.Remove(studentToDelete);
    deleteContext.SaveChanges();
}

Console.WriteLine("DELETE: Student record removed.");

// FINAL CHECK: Confirm there are no records.
using (var finalCheckContext = new AppDbContext())
{
    var totalStudents = finalCheckContext.Students.Count();
    Console.WriteLine($"FINAL CHECK: Total students in database = {totalStudents}");
}

Console.WriteLine("CRUD demonstration complete.");

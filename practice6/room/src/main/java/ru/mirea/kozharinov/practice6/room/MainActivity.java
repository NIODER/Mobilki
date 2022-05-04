package ru.mirea.kozharinov.practice6.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.mirea.kozharinov.practice6.room.db.AppDataBase;
import ru.mirea.kozharinov.practice6.room.db.Employee;
import ru.mirea.kozharinov.practice6.room.db.EmployeeDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDataBase appDataBase = App.getInstance().getDataBase();
        EmployeeDAO employeeDAO = appDataBase.employeeDAO();

        Employee employee = new Employee();
        employee.id = 1;
        employee.name = "John Smith";
        employee.salary = 10000;
        employeeDAO.insert(employee);
        Log.d("1_____________", String.valueOf(employeeDAO.getById(1).salary));
        List<Employee> employees = employeeDAO.getAll();
        employee = employeeDAO.getById(1);
        employee.salary = 20000;
        employeeDAO.update(employee);
        Log.d("2_____________", String.valueOf(employeeDAO.getById(1).salary));
    }
}
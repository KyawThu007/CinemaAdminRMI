/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.AdminAnalysisInterface;
import Interface.BuySeatInterface;
import Interface.CinemaInterface;
import Interface.EmployeeInterface;
import Interface.ExpenseInterface;
import Interface.LoginInterface;
import Interface.MovieInterface;
import Interface.RoleTypeInterface;
import Interface.RoomInterface;
import Interface.SalaryInterface;
import Interface.ShowInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 *
 * @author DELL
 */
public class Service {

    LoginInterface loginStub = null;
    CinemaInterface cinemaStub = null;
    EmployeeInterface employeeStub = null;
    ExpenseInterface expenseStub = null;
    SalaryInterface salaryStub = null;
    RoleTypeInterface roleTypeStub = null;
    RoomInterface roomStub = null;
    AdminAnalysisInterface adminAnalysisStub = null;
    MovieInterface movieStub = null;
    ShowInterface showStub = null;
    BuySeatInterface buySeatStub = null;
    int port=SharedData.getInstance().getPort();

    public LoginInterface getLoginStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            loginStub = (LoginInterface) registry.lookup("login");
            if (loginStub == null) {
                throw new RuntimeException("Login interface not found");
            }

        } catch (RemoteException | NotBoundException e) {
            //e.printStackTrace();
        }
        return loginStub;
    }

    public CinemaInterface getCinemaStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            cinemaStub = (CinemaInterface) registry.lookup("cinema");
            if (cinemaStub == null) {
                throw new RuntimeException("Cinema interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return cinemaStub;
    }

    public EmployeeInterface getEmployeeStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            employeeStub = (EmployeeInterface) registry.lookup("employee");
            if (employeeStub == null) {
                throw new RuntimeException("Employee interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return employeeStub;
    }

    public ExpenseInterface getExpenseStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            expenseStub = (ExpenseInterface) registry.lookup("expense");
            if (expenseStub == null) {
                throw new RuntimeException("Expense interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
            //e.printStackTrace();
        }
        return expenseStub;
    }

    public SalaryInterface getSalaryStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            salaryStub = (SalaryInterface) registry.lookup("salary");
            if (salaryStub == null) {
                throw new RuntimeException("Salary interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
          //  e.printStackTrace();
        }
        return salaryStub;
    }

    public RoleTypeInterface getRoleTypeStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            roleTypeStub = (RoleTypeInterface) registry.lookup("role");
            if (roleTypeStub == null) {
                throw new RuntimeException("Role Type interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return roleTypeStub;
    }

    public RoomInterface getRoomStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            roomStub = (RoomInterface) registry.lookup("room");
            if (roomStub == null) {
                throw new RuntimeException("Room interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return roomStub;
    }

    public AdminAnalysisInterface getAdminAnalysisStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            adminAnalysisStub = (AdminAnalysisInterface) registry.lookup("adminAnalysis");
            if (adminAnalysisStub == null) {
                throw new RuntimeException("Admin Analysis interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return adminAnalysisStub;
    }

    public MovieInterface getMovieStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            movieStub = (MovieInterface) registry.lookup("movie");
            if (movieStub == null) {
                throw new RuntimeException("Movie interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return movieStub;
    }

    public ShowInterface getShowStub() {
         try {
             Registry registry = LocateRegistry.getRegistry("localhost", port);
            showStub = (ShowInterface) registry.lookup("show");
            if (showStub == null) {
                throw new RuntimeException("Show interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
           // e.printStackTrace();
        }
        return showStub;
    }

    public BuySeatInterface getBuySeatStub() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            buySeatStub = (BuySeatInterface) registry.lookup("buySeat");
            if (buySeatStub == null) {
                throw new RuntimeException("Buy Seat interface not found");
            }
        } catch (RemoteException | NotBoundException e) {
            //e.printStackTrace();
        }
        return buySeatStub;
    }

}

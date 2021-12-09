package org.itstep.workaccounting.controllers;

import org.hibernate.jdbc.Work;
import org.itstep.workaccounting.configs.StaticConfig;
import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Role;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.WorkProject;
import org.itstep.workaccounting.models.TaskModel;
import org.itstep.workaccounting.models.UserModel;
import org.itstep.workaccounting.models.WorkProjectModel;
import org.itstep.workaccounting.services.ProjectService;
import org.itstep.workaccounting.services.TaskService;
import org.itstep.workaccounting.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public AdminController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping(value = "/")
    public String indexPage(Model model){
        return "admin";
    }

    @GetMapping(value = "register")
    public String registerPage(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "register";
    }

    @PostMapping(value="register")
    public String registerUser(@ModelAttribute UserModel userModel){
        if(userService.getUser(userModel.getEmail())==null){
            List<Role> roles = new ArrayList<Role>();
            roles.add(StaticConfig.ROLE_USER);
            if(userModel.getRole().equals("ROLE_ADMIN"))
                roles.add(StaticConfig.ROLE_ADMIN);
            else if(userModel.getRole().equals("ROLE_MANAGER"))
                roles.add(StaticConfig.ROLE_MANAGER);
            var dbUser = new DbUser(userModel.getEmail(), userModel.getPassword(), userModel.getFullName(), roles);
            userService.registerUser(dbUser);
            return "redirect:/admin/users-list";
        }
        else if(userService.getUser(userModel.getEmail()) != null){
            return "redirect:/admin/register?error=2";
        }
        else{
            return "redirect:/admin/register?error=1";
        }
    }

    @GetMapping(value = "project-create")
    public String projectCreatePage(Model model) {
        model.addAttribute("workProjectModel", new WorkProjectModel());
        return "project-create";
    }

    @PostMapping(value="project-create")
    public String projectCreatePage(@ModelAttribute WorkProjectModel workProjectModel){
        if(workProjectModel!=null){
            var project = new WorkProject(workProjectModel.getName(), workProjectModel.getDescription());
            projectService.addProject(project);
            return "redirect:/admin/";
        }
        else{
            return "redirect:/admin/project-create?error";
        }
    }

    @GetMapping(value = "add-task")
    public String addTask(Model model) {
        List<DbUser> usersList = userService.getUsers();
        List<WorkProject> projectList = projectService.getProjects();

        model.addAttribute("taskModel", new TaskModel());
        model.addAttribute("users", usersList);
        model.addAttribute("projects", projectList);
        return "add-task";
    }

    @PostMapping(value="add-task")
    public String addTask(@ModelAttribute TaskModel taskModel){
        if(taskModel!=null){
            WorkProject project = projectService.getProject(taskModel.getProject_id());
            if(project!=null){
                var task = new Task(taskModel.getName(), taskModel.getDescription(), "todo", userService.getUser(taskModel.getUser_id()));
                taskService.addTask(task);
                project.getTasks().add(task);
                projectService.addProject(project);
            }
            return "redirect:/admin/";
        }
        else{
            return "redirect:/admin/project-create?error";
        }
    }

    @GetMapping("/users-list")
    public String getAllUsers(Model model,
                                @RequestParam(defaultValue = "0") Integer pageNO,
                                @RequestParam(defaultValue = "5") Integer pageSize){
        List<DbUser> all = userService.getUsersPaged(pageNO, pageSize);
        Integer usersCount = userService.getUsers().size();

        model.addAttribute("users", all);
        model.addAttribute("pageNO", pageNO);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("usersCount", usersCount);

        return "users-list";
    }
}

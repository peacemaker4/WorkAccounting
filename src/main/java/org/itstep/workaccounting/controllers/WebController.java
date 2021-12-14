package org.itstep.workaccounting.controllers;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.TaskDescription;
import org.itstep.workaccounting.entities.WorkProject;
import org.itstep.workaccounting.models.ProjectTaskModel;
import org.itstep.workaccounting.models.TaskDescriptionModel;
import org.itstep.workaccounting.models.TaskModel;
import org.itstep.workaccounting.services.ProjectService;
import org.itstep.workaccounting.services.TaskService;
import org.itstep.workaccounting.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;

    public WebController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping(value = "login")
    public String loginPage(){
        return "login";
    }

//    @GetMapping(value = "/")
//    public String indexPage(Model model){
//        model.addAttribute("user", getUser());
//        List<Task> tasks = taskService.getAllTasksOfUser(getUser());
//
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("today", LocalDate.now());
//        model.addAttribute("taskService", taskService);
//        return "index";
//    }

    @RequestMapping(value = "/")
    public String indexPageSearch(Model model, @Param("keyword") String keyword){
        model.addAttribute("main_user", getUser());
        List<Task> tasks = taskService.findTasks(getUser(), keyword);
        List<DbUser> users = new ArrayList<>();
        List<WorkProject> projects = new ArrayList<>();

        for(var t : tasks){
            if(!projects.contains(t.getProject())){
                projects.add(t.getProject());
            }
        }

        for(var p : projects){
            List<Task> project_tasks = taskService.getAllTasksOfProject(p);
            for(var u : project_tasks){
                if(!users.contains(u.getUser()) && u.getUser() != getUser()){
                    users.add(u.getUser());
                }
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tasks", tasks);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("taskService", taskService);
        return "index";
    }

    @RequestMapping(value = "/managers/users")
    public String usersIndex(Model model, @Param("keyword") String keyword, @Param("user_id") Long user_id){
        DbUser user = userService.getUser(user_id);
        model.addAttribute("main_user", user);
        List<Task> tasks = taskService.findTasks(user, keyword);
        List<DbUser> users = new ArrayList<>();
        List<WorkProject> projects = new ArrayList<>();

        for(var t : tasks){
            if(!projects.contains(t.getProject())){
                projects.add(t.getProject());
            }
        }

        for(var p : projects){
            List<Task> project_tasks = taskService.getAllTasksOfProject(p);
            for(var u : project_tasks){
                if(!users.contains(u.getUser()) && u.getUser() != user && u.getUser() != getUser()){
                    users.add(u.getUser());
                }
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tasks", tasks);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("taskService", taskService);
        return "index-readonly";
    }

    @GetMapping("/managers/tasks/{id}")
    public String usersTask(Model model, @PathVariable("id") Long id){
        Task task = taskService.getTask(id);
        TaskDescriptionModel taskModel = new TaskDescriptionModel();
        if(task.getTaskDescription(LocalDate.now()) != null){
            TaskDescription taskD = task.getTaskDescription(LocalDate.now());
            taskModel = new TaskDescriptionModel(taskD.getTime_from().toString(), taskD.getTime_to().toString(), taskD.getJobType(), taskD.getComment());
        }
        model.addAttribute("taskModel", taskModel);
        model.addAttribute("task", task);
        model.addAttribute("user", getUser());
        return "task-readonly";
    }

    @GetMapping("/tasks/{id}")
    public String getTask(Model model, @PathVariable("id") Long id){
        Task task = taskService.getTask(id);
        TaskDescriptionModel taskModel = new TaskDescriptionModel();
        if(task.getTaskDescription(LocalDate.now()) != null){
            TaskDescription taskD = task.getTaskDescription(LocalDate.now());
            taskModel = new TaskDescriptionModel(taskD.getTime_from().toString(), taskD.getTime_to().toString(), taskD.getJobType(), taskD.getComment());
        }
        model.addAttribute("taskModel", taskModel);
        model.addAttribute("task", task);
        model.addAttribute("user", getUser());
        return "task";
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(@ModelAttribute TaskDescriptionModel taskDescriptionModel, @PathVariable("id") Long id) throws ParseException {
        Task task = taskService.getTask(id);
        List<Task> userTasks = taskService.getAllTasksOfUser(getUser());

        List<TaskDescription> todays_descriptions = new ArrayList<>();

        for(var t : userTasks){
            TaskDescription tmp = t.getTaskDescription(LocalDate.now());
            if(tmp != null){
                todays_descriptions.add(tmp);
            }
        }

        Integer count = 0;
        TaskDescription newDesc = new TaskDescription();
        newDesc.setTime_to(LocalTime.parse(taskDescriptionModel.getTime_to()));
        newDesc.setTime_from(LocalTime.parse(taskDescriptionModel.getTime_from()));
        todays_descriptions.add(newDesc);

        for(var d : todays_descriptions){
            for(var f: todays_descriptions){
                if(d != f){
                    if(!d.getTime_to().isBefore(f.getTime_from()) && !d.getTime_from().isAfter(f.getTime_to())){
                        count++;
                        break;
                    }
                }
            }

        }

        if(count > 3){
            return "redirect:/tasks/{id}?error=1";
        }

        if(task != null){
            TaskDescription description = new TaskDescription();

            if(task.getTaskDescription(LocalDate.now()) == null){
                description = new TaskDescription(LocalTime.parse(taskDescriptionModel.getTime_from()), LocalTime.parse(taskDescriptionModel.getTime_to()), taskDescriptionModel.getJobType(), taskDescriptionModel.getComment(), LocalDate.now());
                taskService.addTaskDescription(description);
                task.getTaskDescriptions().add(description);
            }
            else{
                description = task.getTaskDescription(LocalDate.now());
                int index = task.getTaskDescriptions().indexOf(description);
                description.setTime_from(LocalTime.parse(taskDescriptionModel.getTime_from()));
                description.setTime_to(LocalTime.parse(taskDescriptionModel.getTime_to()));
                description.setJobType(taskDescriptionModel.getJobType());
                description.setComment(taskDescriptionModel.getComment());
                taskService.addTaskDescription(description);
                task.getTaskDescriptions().set(index, description);
            }
            task.setStatus("doing");
            taskService.addTask(task);
        }

        return "redirect:/";
    }

    @RequestMapping("/users/{id}/stats")
    public String userStats(Model model, @PathVariable("id") Long id, @Param("date") String date){
        model.addAttribute("user", getUser());
        List<Task> tasks = taskService.getAllTasksOfUser(getUser());

        if(date == null)
            date = LocalDate.now().toString();
        model.addAttribute("date", LocalDate.parse(date));
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", getUser());
        return "stats";
    }

    @RequestMapping("/managers/users/{id}/stats")
    public String userStatsManage(Model model, @PathVariable("id") Long id, @Param("date") String date){
        model.addAttribute("user", userService.getUser(id));
        List<Task> tasks = taskService.getAllTasksOfUser(userService.getUser(id));

        if(date == null)
            date = LocalDate.now().toString();
        model.addAttribute("date", LocalDate.parse(date));
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", userService.getUser(id));
        return "stats";
    }

    @RequestMapping("/managers/{id}/stats")
    public String managerStats(Model model, @PathVariable("id") Long id, @Param("date") String date){
        model.addAttribute("user", getUser());
        List<Task> tasks = taskService.getAllTasksOfUser(getUser());
        List<WorkProject> projects = new ArrayList<>();

        for(var t : tasks){
            if(!projects.contains(t.getProject())){
                projects.add(t.getProject());
            }
        }

        Map<WorkProject, List<Task>> dictionary = new HashMap<>();

        for(var p : projects){
            dictionary.put(p, taskService.getAllTasksOfProject(p));
        }

        if(date == null)
            date = LocalDate.now().toString();
        model.addAttribute("date", LocalDate.parse(date));
        model.addAttribute("dictionary", dictionary);
        model.addAttribute("user", getUser());
        return "manager-stats";
    }

    private DbUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User securityUser = (User) authentication.getPrincipal();
            DbUser user = userService.getUser(securityUser.getUsername());
            return user;
        }
        return null;
    }


}


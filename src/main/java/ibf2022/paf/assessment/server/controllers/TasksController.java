package ibf2022.paf.assessment.server.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;

// TODO: Task 4, Task 8
@Controller
@RequestMapping
public class TasksController {

    @Autowired
    TodoService todoService;

    @PostMapping("/task")
    public ModelAndView getTasks(@RequestBody MultiValueMap<String, String> formData) {
        List<Task> listofTasks = new LinkedList<>();
        int count = 0;
        int noOfTasks = formData.size()/3;

        while (count < noOfTasks) {
            Task task = new Task();
            task.setUsername(formData.getFirst("username"));
            task.setDescription(formData.getFirst("description-" + Integer.toString(count)));
            task.setPriority(Integer.parseInt(formData.getFirst("priority-" + Integer.toString(count))));
            task.setDueDate(Date.valueOf(formData.getFirst("dueDate-" + Integer.toString(count))));
            listofTasks.add(task);
            count++;
        }

        try {
            todoService.upsertTask(listofTasks);
            String taskCount = Integer.toString(listofTasks.size());
            String username = listofTasks.get(0).getUsername();
            Map<String, String> models = new HashMap<>();
            models.put("taskCount", taskCount);
            models.put("username", username);

            return new ModelAndView("result", models, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ModelAndView("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    
}

package ibf2022.paf.assessment.server.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.Exception.InsertTaskException;
import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6
@Repository
public class TaskRepository {

    private final static String INSERT_TASK = "insert into task (user_id_fk, task_description, priority, due_date) values (?,?,?,?)";

    @Autowired
    private JdbcTemplate template;

    public Boolean insertTask(List<Task> listOfTasks, String userId) {
        // update method returns 0 if update fails
        for (int i = 0; i<listOfTasks.size(); i++) {

            Integer updated = template.update(INSERT_TASK, userId, listOfTasks.get(i).getDescription(), 
            listOfTasks.get(i).getPriority(), listOfTasks.get(i).getDueDate());

            if (updated == 0) 
                throw new InsertTaskException("Insert Task Fails.");
            
            
        }
        return true;
    }
}

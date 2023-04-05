package ibf2022.paf.assessment.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.Exception.InsertTaskException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = InsertTaskException.class)
    public void upsertTask(List<Task> listOftasks) throws InsertTaskException {

        String username = listOftasks.get(0).getUsername();

        if (!userRepository.findUserByUsername(username).isPresent()) {
            User user = new User();
            String name = username.substring(0,1).toUpperCase() 
                            + username.substring(1);
            user.setUsername(username);
            user.setName(name);
            String generatedUUID = userRepository.insertUser(user);
        
        }
        User user = userRepository.findUserByUsername(username).get();
        taskRepository.insertTask(listOftasks, user.getUserId());

    }
}

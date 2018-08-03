package com.gmail.ribil39.repository;

import com.gmail.ribil39.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{


}

/*Резюме по первому обсуждению орг. вопросов:

1. Вначале реализовываем взаимодействие через REST, как описано в задании.
2. Формат:

 - первым get запрос -  в header "new_user":"true"
 - ответ от бекенда json формата {"uid":"..."}

 - далее с этим uid post запросы на передачу сообщения вида: json {"uid":"...", "text":"..."}

 - запрос на синхронизацию/получение новых сообщений -  get, в header "uid":""
 - ответ от бекенда - json  формата
{
"number_of_msgs":"...",
"msgs": [
{"name":"...", "text":"...", "date":"..."},
...
]
}
*/
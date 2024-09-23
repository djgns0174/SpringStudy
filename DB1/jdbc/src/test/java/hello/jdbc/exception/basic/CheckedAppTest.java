package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {

    @Test
    void callCheck(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.call())
                .isInstanceOf(Exception.class);
    }

    static class Controller{
        public Controller(){}

        public void call() throws SQLException, ConnectException {
            Service service = new Service();
            service.call();
        }
    }

    static class Service{
        public Service(){}

        public void call() throws SQLException, ConnectException{
            Repository repository = new Repository();
            NetWorkClient netWorkClient = new NetWorkClient();

            repository.call();
            netWorkClient.call();
        }
    }

    static class Repository{
        public void call() throws SQLException {
            throw new SQLException("database err");
        }
    }

    static class NetWorkClient{
        public void call() throws ConnectException {
            throw new ConnectException("연결실패");
        }
    }
}

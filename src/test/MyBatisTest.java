import com.caogen.dao.SysUserMapper;
import com.caogen.domain.SysUser;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by neal on 11/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
public class MyBatisTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @org.junit.Test
    public void findUserTest(){
        SysUser user = sysUserMapper.selectByPK(1L);
        System.out.println(user.getUsername());
    }
}

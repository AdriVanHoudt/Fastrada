package be.fastrada;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class RaceControllerTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetRaces() throws Exception {
        this.mockMvc.perform(get("/api/race").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk());
    }

    /*
     * The id's below were taken from the db
     * if the tests fail make sure these id's match to an actual document
     * This is because the id's are generated by Mongodb so we can't know them upfront
     */
    @Test
    public void testGetRaceById() throws Exception {
        this.mockMvc.perform(get("/api/race/5320240e0cf23bd4ea0ce01e").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk());
    }

    @Test
    public void testGetRaceDataById() throws Exception {
        this.mockMvc.perform(get("/api/race/53207f830cf2b2f9532f997b/data/speed").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk());
        this.mockMvc.perform(get("/api/race/53207f830cf2b2f9532f997b/data/").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk());

    }
}

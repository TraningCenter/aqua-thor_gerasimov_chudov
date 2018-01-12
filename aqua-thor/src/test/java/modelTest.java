import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.interfaces.IStream;
import com.infotech.aquaThor.model.Application;
import com.infotech.aquaThor.model.Field;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Config;
import com.infotech.aquaThor.model.utils.Orientation;
import com.infotech.aquaThor.model.utils.Tuple;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import com.infotech.aquaThor.view.parsers.SaxParser;
import com.infotech.aquaThor.view.parsers.StaxParser;
import com.infotech.aquaThor.view.parsers.domParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class modelTest extends Assert{

    Model model;
    IParser parser;
    Application app;

    @Before
    public void setUp(){
        parser = new domParser();
        Config.setDelay(0);
    }

    @Test
    public void test1(){
        try {
            model = parser.parse("configurations/testInput.xml");
            model.bypassAllElements();
        }catch (Exception e){

        }
        assertEquals(1,model.getFishes().size());
    }

    @Test
    public void test2(){
        try{
        model = parser.parse("configurations/testInput2.xml");
        model.bypassAllElements();
        }catch (Exception e){

        }
        long newX = model.getFishes().get(0).getCoordinates().x;
        assertEquals(4,newX);
    }

    @Test
    public void test3(){
        try{
            model = parser.parse("configurations/testInput3.xml");
            model.bypassAllElements();
        }catch (Exception e){

        }
        long newY = model.getFishes().get(0).getCoordinates().y;
        assertEquals(2,newY);
    }

    @Test
    public void test4(){
        try{
            model = parser.parse("configurations/testInput4.xml");
            model.bypassAllElements();
        }catch (Exception e){

        }
        long newY = model.getFishes().get(0).getCoordinates().y;
        assertEquals(4,newY);
    }
}

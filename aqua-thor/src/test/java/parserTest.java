
import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.interfaces.IStream;
import com.infotech.aquaThor.model.Field;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.model.entities.Stream;
import com.infotech.aquaThor.model.utils.Orientation;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alegerd
 */
public class parserTest extends Assert{
    
    private IParser dom;
    private IParser sax;
    private IParser stax;
    private IParser jaxb;
   
    @Before
    public void setUp(){
        dom = new domParser();
        sax = new SaxParser();
        stax = new StaxParser();
        jaxb = new JaxbParser();      
    }
    
    @Test
    public void domTest(){
        IField field = new Field(10,15,true);
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        Fish fish1 = new Fish(15,15,2,4,5);
        fish1.setXCoord(3);
        fish1.setYCoord(5);
        fishes.add((IFish)fish1);
        Shark fish2 = new Shark(15,15,2,4,7);
        fish2.setXCoord(3);
        fish2.setYCoord(6);
        fishes.add((IFish)fish2);
        Stream stream = new Stream(12,17,1);
        stream.setOrientation(Orientation.HORIZONTAL);
        streams.add((IStream)stream);
        Model model = new Model(field, fishes, streams);
        String actual = new String();
        try{
            actual = dom.parse("configurations/testInput.xml").toString();
        }catch(Exception e){}
        assertEquals(model.toString(), actual);
    }
    
        
    @Test
    public void saxTest(){
        IField field = new Field(10,15,true);
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        Fish fish1 = new Fish(15,15,2,4,5);
        fish1.setXCoord(3);
        fish1.setYCoord(5);
        fishes.add((IFish)fish1);
        Shark fish2 = new Shark(15,15,2,4,7);
        fish2.setXCoord(3);
        fish2.setYCoord(6);
        fishes.add((IFish)fish2);
        Stream stream = new Stream(12,17,1);
        stream.setOrientation(Orientation.HORIZONTAL);
        streams.add((IStream)stream);
        Model model = new Model(field, fishes, streams);
        String actual = new String();
        try{
            actual = sax.parse("configurations/testInput.xml").toString();
        }catch(Exception e){}
        assertEquals(model.toString(), actual);
    }

    @Test
    public void staxTest(){
        IField field = new Field(10,15,true);
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        Fish fish1 = new Fish(15,15,2,4,5);
        fish1.setXCoord(3);
        fish1.setYCoord(5);
        fishes.add((IFish)fish1);
        Shark fish2 = new Shark(15,15,2,4,7);
        fish2.setXCoord(3);
        fish2.setYCoord(6);
        fishes.add((IFish)fish2);
        Stream stream = new Stream(12,17,1);
        stream.setOrientation(Orientation.HORIZONTAL);
        streams.add((IStream)stream);
        Model model = new Model(field, fishes, streams);
        String actual = new String();
        try{
            actual = stax.parse("configurations/testInput.xml").toString();
        }catch(Exception e){}
        assertEquals(model.toString(), actual);
    }
    
    @Test
    public void jaxbTest(){
        IField field = new Field(10,15,true);
        List<IFish> fishes = new ArrayList<>();
        List<IStream> streams = new ArrayList<>();
        Fish fish1 = new Fish(15,15,2,4,5);
        fish1.setXCoord(3);
        fish1.setYCoord(5);
        fishes.add((IFish)fish1);
        Shark fish2 = new Shark(15,15,2,4,7);
        fish2.setXCoord(3);
        fish2.setYCoord(6);
        fishes.add((IFish)fish2);
        Stream stream = new Stream(12,17,1);
        stream.setOrientation(Orientation.HORIZONTAL);
        streams.add((IStream)stream);
        Model model = new Model(field, fishes, streams);
        String actual = new String();
        try{
            actual = jaxb.parse("configurations/testInput.xml").toString();
        }catch(Exception e){}
        assertEquals(model.toString(), actual);
    }
}


import com.infotech.aquaThor.database.Database;
import com.infotech.aquaThor.database.IStep;
import com.infotech.aquaThor.database.JaxbOutput;
import com.infotech.aquaThor.database.Statistics;
import com.infotech.aquaThor.database.Step;
import com.infotech.aquaThor.interfaces.IFish;
import com.infotech.aquaThor.interfaces.IParser;
import com.infotech.aquaThor.model.Model;
import com.infotech.aquaThor.model.entities.Fish;
import com.infotech.aquaThor.model.entities.Shark;
import com.infotech.aquaThor.view.parsers.JaxbParser;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class DatabaseTest extends Assert{
    
    JaxbOutput outputWriter;
    IParser parser;
    Statistics stats;
    Model model;
    
    @Before
    public void setUp(){
        outputWriter = new JaxbOutput("configurations/testOutput.xml");
        parser = new JaxbParser();
        try{
            model = parser.parse("configurations/input.xml");
        } catch (Exception ex){}
        stats = new Statistics(model);
    }
    
    @Test
    public void testOutput(){
        ArrayList<IStep> stepList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            int fishCount = 0;
            int sharkCount = 0;
            for (IFish fish : model.getFishes()){
                if (fish.getClass().equals(Fish.class)){
                    fishCount++;
                }
                if (fish.getClass().equals(Shark.class)){
                    sharkCount++;
                }
            }
            
            stepList.add(new Step(i, fishCount, sharkCount));
            stats.generate(i);
            try{
                model.bypassAllElements();
            } catch (Exception ex){}
        }
        outputWriter.writeDatabase(stats.getDatabase());
        
        Database db;
        try{
            db = outputWriter.readDatabase();
            ArrayList<IStep> inputList = db.getSteps().getStepsList();
            assertEquals(stepList.size(), inputList.size());
            for (int i = 0; i < stepList.size(); i++){
                assertEquals(stepList.get(i), inputList.get(i));
            }
        } catch (Exception ex){}
    }
}

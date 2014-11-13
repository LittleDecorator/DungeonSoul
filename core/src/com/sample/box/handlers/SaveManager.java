package com.sample.box.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;


/*ust create a public static SaveManager saveManager= new SaveManager() in your GameProject class (the class that extends Game) and when you want to use your save manager simply call GameProject.saveManager!*/
public class SaveManager {

    //encrypted saves or not-encrypted saves.
    private boolean encoded;

    //internal files are read-only.
    //We save it in the bin, because of how the project is compiled, the bin is the location where all the class files are being compiled to (simplified: It’s the location where the files are that you execute)
    private FileHandle file = Gdx.files.local("bin/save.json");

    //we’re going to put all our data
    public static class Save{
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }

    private Save save = getSave();

    public SaveManager(boolean encoded){
        this.encoded = encoded;
        save = getSave();
    }

    private Save getSave(){
        Save save = new Save();

        //check if file exists
        if(file.exists()){
            Json json = new Json();
            //if need decode then code/decode
            if(encoded) {
                save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
            } else {
                save = json.fromJson(Save.class,file.readString());
            }
        }
        return save;
    }

    public void saveToJson(){
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        if(encoded){
            file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
        }else{
            file.writeString(json.prettyPrint(save), false);
        }
    }

    //loading a specific value
    @SuppressWarnings("unchecked")
    public <T> T loadDataValue(String key, Class type){
        if(save.data.containsKey(key)){
            return (T) save.data.get(key);
        }else{
            return null;   //this if() avoids and exception, but check for null on load.
        }
    }

    public void saveDataValue(String key, Object object){
        save.data.put(key, object);
        saveToJson(); //Saves current save immediatly.
    }

    public ObjectMap<String, Object> getAllData(){
        return save.data;
    }
}

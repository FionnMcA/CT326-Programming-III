public class TestCentre {

    //Test centre class that requires a name and address as parameters
    private String name;
    private String address;

    public TestCentre(String name, String address){
        this.name = name;
        this.address = address;
    }

    //getters to retrieve the name and address
    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }
}

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Graph<E> {

    public ArrayList<ArrayList<E>> data;

    public Graph(){
        this.data = new ArrayList<ArrayList<E>>();
    }

    public void addVertex(E ver) throws VertexExistException{

        for (ArrayList<E> al : data)
            if (al.get(0).equals(ver))
                return;
        ArrayList<E> temp = new ArrayList<E>();
        temp.add(ver);
        this.data.add(temp);
    }

    public void addEdge(E ver1, E ver2) throws VertexNotExistException{

        if (this.data == null || this.data.size() == 0){
            return;
        }

        boolean ver1IsFound = false, ver2IsFound = false;
        int ver1Index=-1, ver2Index=-1;

        for (ArrayList<E> v : this.data) {
            if (v.get(0).equals(ver1)) {
                ver1IsFound = true;
                ver1Index = this.data.indexOf(v);
            }
            if (v.get(0).equals(ver2)) {
                ver2IsFound = true;
                ver2Index = this.data.indexOf(v);
            }
        }

        if (!ver1IsFound || !ver2IsFound)
            return;

        if (!this.data.get(ver1Index).contains(ver2))
            this.data.get(ver1Index).add(ver2);
        if (!this.data.get(ver2Index).contains(ver1))
            this.data.get(ver2Index).add(ver1);
    }

    public ArrayList<E> getEdges(E ver) throws VertexNotExistException{

        if (this.data == null || this.data.size() == 0){
            return null;
        }

        boolean verIsFound = false;
        int verIndex = -1;

        for (ArrayList<E> v : this.data) //checking if ver crossroads exists
            if (v.get(0).equals(ver)) {
                verIsFound = true;
                verIndex = this.data.indexOf(v);
            }
        if (!verIsFound) //no such crossroads
            return null;
        if (this.data.get(verIndex).size() == 1) //no edges
            return null;

        ArrayList<E> temp = new ArrayList<>();
        for (E e : this.data.get(verIndex))
            if (!e.equals(ver))
                temp.add(e);
        return temp;
    }

    public ArrayList<E> getVertices(){
        if (this.data == null || this.data.size() == 0){
            return null;
        }

        ArrayList<E> temp = new ArrayList<>();
        for (ArrayList<E> ver : this.data)
            temp.add(ver.get(0));

        return temp;
    }

    public ArrayList<E> bfs(E from, E to) {
        if (this.data==null || this.data.isEmpty()) //null check
            return null;

        boolean sourceIsFound=false, targetIsFound=false;
        for(ArrayList<E> v : this.data) { //checking if Source and Target exist
            if (v.get(0).equals(from))
                sourceIsFound=true;
            if (v.get(0).equals(to))
                targetIsFound=true;
        }
        if (!sourceIsFound || !targetIsFound)
            return null;

        //Starting Path Finding Algorithm
        ArrayList<ArrayList<E>> routes = new ArrayList<ArrayList<E>>();
        ArrayList<E> start = new ArrayList<>();
        start.add(from);
        routes.add(start);

        if(from.equals(to))
            return start;

        return bfs(from, to, routes); //Recursive private method
    }

    private ArrayList<E> bfs(E source, E target, ArrayList<ArrayList<E>> routes) {
        if(routes.isEmpty()) //Recursive Stop Condition
            return null;

        ArrayList<E> t1 = routes.remove(0);
        E extracted = t1.remove(t1.size()-1);

        for (E e : getEdges(extracted)) {
            ArrayList<E> temp = new ArrayList<>();
            for(E t : t1)
                temp.add(t);
            temp.add(extracted);
            temp.add(e);
            if(!hasDuplicates(temp)) //private method - if vector has duplicates dont add it to possible routes.
                routes.add(temp);
            if (e.equals(target)) //Target was reached.
                return temp;
        }
        return bfs(source, target, routes);
    }

    private boolean hasDuplicates(ArrayList<E> vec) {
        if(vec==null || vec.isEmpty())
            return false;

        for (int i=0; i < vec.size(); i++) {
            int counter=0;
            for (int j=0; j < vec.size(); j++) {
                if (vec.get(i).equals(vec.get(j)))
                    counter++;
            }
            if (counter > 1)
                return true;
        }
        return false;
    }

    }


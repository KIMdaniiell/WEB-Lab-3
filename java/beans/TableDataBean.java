package beans;

import entities.DotEntity;
import utils.AreaChecker;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@ManagedBean
@ApplicationScoped
public class TableDataBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private String result;
    private LocalDateTime current_time ;
    private double processing_time ;

    @EJB
    private Ejbean ejbean;


    public TableDataBean() {
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        if (r == 0){
            r = 1;
        }
        return r;
    }

    public String getResult() {
        return result;
    }

    public String getCurrentTime() {
        return current_time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public double getProcessingTime() {
        return processing_time;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setCurrent_time(LocalDateTime current_time) {
        this.current_time = current_time;
    }

    public void setProcessing_time(double processing_time) {
        this.processing_time = processing_time;
    }

    public void process(){
        long start = System.nanoTime();
        try {
            if (AreaChecker.isHit(x,y,r)){
                result = "HIT";
            } else {
                result = "MISS";
            }
        } catch (NumberFormatException e){
            result = "NFException";
        }
        processing_time = System.nanoTime() - start;
        current_time = LocalDateTime.now();

        DotEntity dotEntity = new DotEntity();
        dotEntity.setX(x);
        dotEntity.setY(y);
        dotEntity.setR(r);
        dotEntity.setResult(result);
        dotEntity.setProcessing_time(processing_time);
        dotEntity.setCurrent_time(current_time);
        ejbean.loadDot(dotEntity);

    }

    public LinkedList getBeanList(){
        return ejbean.getDots();
    }

    public void resetList(){
        ejbean.resetDots();
    }
}
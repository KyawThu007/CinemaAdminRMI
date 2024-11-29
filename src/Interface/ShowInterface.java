/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Show;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface ShowInterface {

    public int addShow(Show show);

    public void updateShow(Show show);

    public void deleteShow(int id);

    public List<Show> getAllShowMovie(int cinema_id,String startDate, String endDate, String showTime);

    public Show getShowMovie(int id);

    public void logMessage(String log);
}

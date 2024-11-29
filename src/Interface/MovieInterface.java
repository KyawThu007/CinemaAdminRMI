/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Movie;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface MovieInterface {

    public int addMovie(Movie movie);

    public void updateMovie(Movie movie);

    public void deleteMovie(int id);

    public List<Movie> getAllMovie();
    
    public Movie getMovie(int id);
    
    public void logMessage(String log);
}

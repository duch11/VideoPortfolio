package dk.centralmediaproductions.videoportfolioapp.Controllers;

import dk.centralmediaproductions.videoportfolioapp.Entities.Video;
import dk.centralmediaproductions.videoportfolioapp.Repositories.VideoRepository;
import dk.centralmediaproductions.videoportfolioapp.Utilities.DeveloperModeUtil;
import dk.centralmediaproductions.videoportfolioapp.Utilities.NavbarUtil;
import dk.centralmediaproductions.videoportfolioapp.Utilities.SortByRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
public class VideoController {

    private boolean developermode = true;

    @Autowired
    VideoRepository videoRepository;

    @RequestMapping(value = "/addFilm", method = RequestMethod.GET)
    public String addVideo(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam String videoUrl,
                           @RequestParam String photoUrl,
                           @RequestParam int rankNumber,
                           @RequestParam String genre){

        Video video = new Video(title, description, videoUrl, photoUrl, rankNumber, genre);
        videoRepository.save(video);
        System.out.println("Succesfully saved video to database");
        return "saved";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showVideoGrid(Model model){
        new NavbarUtil().highlightVideoGrid(model);
        new DeveloperModeUtil().setDevelopermode(model, developermode);
        //sorterer liste
        ArrayList<Video> listSortedByRank = new SortByRank().getListByRank(videoRepository.findAll());
        System.out.println("succes1");
        model.addAttribute("videos", listSortedByRank);
        System.out.println("succes2");
        return "videoGrid";
    }



}

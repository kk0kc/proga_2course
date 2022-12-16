package services;

import models.Fav;
import models.Post;
import repositories.PostsRepository;
import temp.repositories.FavRepository;

import java.util.List;

public class FavService {
    private static final FavRepository repository = new FavRepository();
    public List<Fav> getAllPosts() {
        return repository.findAll();
    }
    public void updateFav(Fav fav) {
        repository.update(fav);
    }
}

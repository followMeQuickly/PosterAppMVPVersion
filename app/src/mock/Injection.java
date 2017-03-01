import android.content.Context;

import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.local.LocalPosterDataSource;

import data.FakePosterRemoteDataSource;

/**
 * Created by chaseland on 2/8/17.
 */

public class Injection {
    public static PosterRepository provideRepository(Context context) {

        return PosterRepository.GetInstance(FakePosterRemoteDataSource.getInstance(),
                LocalPosterDataSource.getInstance(context));

    }
}

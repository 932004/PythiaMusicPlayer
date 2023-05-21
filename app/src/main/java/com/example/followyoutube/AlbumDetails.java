package com.example.followyoutube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import static com.example.followyoutube.MainActivity.musicFiles;

import com.bumptech.glide.Glide;

public class AlbumDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName;
    ArrayList<MusicFiles> albumSongs=new ArrayList<>();
    AlbumDetailsAdapter albumDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        recyclerView =findViewById(R.id.recyclerView);
        albumPhoto=findViewById(R.id.albumPhoto);
        albumName=getIntent().getStringExtra("albumName");
        int j = 0;
        for(int i =0;i<musicFiles.size();i++){
            if(albumName.equals(musicFiles.get(i).getAlbum()))
            {
                //add(j,musicFiles.get(i)) j是一个可选参数，表示元素插入位置的索引值
                albumSongs.add(j,musicFiles.get(i));
                j ++;
            }
        }
        byte[] image = getAlbumArt(albumSongs.get(0).getPath());
        if(image!=null)
        {
            Glide.with(this)
                    .load(image)
                    .into(albumPhoto);
        }
        else
        {
            Glide.with(this)
                    .load(R.drawable.picture)
                    .into(albumPhoto);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!(albumSongs.size()<1))
        {
            albumDetailsAdapter=new AlbumDetailsAdapter(this,albumSongs);
            recyclerView.setAdapter(albumDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    RecyclerView.VERTICAL,false));

        }
    }

    private  byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
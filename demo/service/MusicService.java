package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Music;

public interface MusicService {
    Iterable<Music> findAll();
    void insertMusic(Music music);
    void deleteMusic(Integer id);
    Optional<Music> findById(Integer id);
	void updateMusic(Music music);
}

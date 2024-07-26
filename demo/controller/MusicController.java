package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Music;
import com.example.demo.form.MusicForm;
import com.example.demo.service.MusicService;

@Controller
public class MusicController {
    
    @Autowired
    MusicService service;
    
    // トップ画面表示
    @GetMapping("top")
    public String topView() {
        return "top";
    }
    
// ----------- top.htmlから一覧画面表示 -------------------------------------------
    @PostMapping(value = "menu", params = "select")
    public String listView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "list";
    }
    
    /** list.htmlからメニューに戻る */
    @GetMapping("function")
    public String selectFunction() {
        return "top";
    }  
// ----------　ここまで一覧　--------------------------------------------------------

    
// ----------- top.htmlから登録画面 ----------------------------------------------
    @PostMapping(value = "menu", params = "insert")
    public String memberInputView(Model model) {
        model.addAttribute("memberForm", new MusicForm());
        return "music-input";
    }
    
    /** music-input.htmlから登録確認 */
    @PostMapping("confirm")
    public String inputView(MusicForm f, Model model) {
        model.addAttribute("memberForm", f);
        return "music-confirm";
    }
    
    /** music-confirm.htmlから登録完了 */
    @PostMapping("complete")
    public String memberConfirmView(MusicForm f) {
        Music music = new Music(
                f.getSong_id(),
                f.getSong_name(),
                f.getSinger()); 
        service.insertMusic(music);
        return "music-complete";
    }
    
    /** music-completeからメニューへ */
    @GetMapping("index")
    public String indexView() {
        return "top";
    }
// ----------　ここまで登録　--------------------------------------------------------
    
    
// ---------- top.htmlから更新画面 -----------------------------------------------
    @PostMapping(value = "menu", params = "update")
    public String updateView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "music-update";
    }
    
    // 更新画面から更新確認画面へ遷移
    @PostMapping("updateMusic")
    public String updateMusic(@RequestParam("id") Integer id, Model model) {
        Optional<Music> music = service.findById(id);
        if (music.isPresent()) {
            model.addAttribute("updatedMusic", music.get());
            return "music-update-confirm";
        }
        return "error"; // エラーページに遷移させる場合
    }
    
    // music-update-confirm.htmlから更新を保存
    @PostMapping("confirmUpdateMusic")
    public String confirmUpdateMusic(MusicForm f) {
        Music music = new Music(
                f.getSong_id(),
                f.getSong_name(),
                f.getSinger());
        service.updateMusic(music);
        return "music-complete";
    }
// ----------　ここまで更新　--------------------------------------------------------
    
    // top.htmlから削除画面
    @PostMapping(value = "menu", params = "delete")
    public String deleteView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "music-delete";
    }
    
    /**削除画面から削除完了画面 */
    @PostMapping("deleteMusic")
    public String deleteMusic(@RequestParam("ids") Integer[] ids, Model model) {
        for (Integer id : ids) {
            service.deleteMusic(id);
        }
        return "music-delete-complete"; 
    }
// ----------　ここまで削除　--------------------------------------------------------

}
package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Bookmark;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    public void add(long postId, long userId) {
        Post post = Post.builder().id(postId).build();
        User user = User.builder().id(userId).build();
        Bookmark bookmark = new Bookmark(post,user);
        bookmarkRepository.save(bookmark);
    }
    public void delete(long postId, long userId) {
        Post post = Post.builder().id(postId).build();
        User user = User.builder().id(userId).build();
        bookmarkRepository.deleteBookmarkByUserAndPost(user, post);
    }

    public boolean checkBookmark(Long postId, Long userId) {

        User user = User.builder().id(userId).build();
        Post post = Post.builder().id(postId).build();

        Optional<Bookmark> bookmark = bookmarkRepository.findBookmarkByUserAndPost(user, post);
        if (bookmark.isEmpty()){
            return false;
        } else{
            return  true;
        }
    }


}

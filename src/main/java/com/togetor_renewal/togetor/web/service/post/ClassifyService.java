package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.DistrictRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassifyService {
    private final PostRepository postRepository;
    private final DistrictRepository districtRepository;
    private final CategoryRepository categoryRepository;

    public List<District> findDistricts(){
        return districtRepository.findAll();
    }

    public List<District> findAllSigunguBySido(String siDo) {
        return districtRepository.findAllBySiDoAndSiGunGuIsNotNullAndEupMyeonDongIsNull(siDo);
    }

    public List<District> findAllEupmyeondongBySidoAndSigungu(String siDo, String siGunGu){
        return districtRepository.findAllBySiDoAndSiGunGuAndEupMyeonDongIsNotNull(siDo, siGunGu);
    }

    public List<Post> findPostsByCategory(String categoryTitle){
        return postRepository.findPostsByCategoryTitleOrderByIdDesc(categoryTitle);
    }

    public List<Post> findPostsByCategoryAndSido(String categoryTitle, String siDo){
        return postRepository.findPostsByCategoryTitleAndSiDoOrderByIdDesc(categoryTitle, siDo);
    }
    public List<Post> findPostsByCategoryAndSidoAndSigunGu(String categoryTitle, String siDo, String siGunGu){
        return postRepository.findPostsByCategoryTitleAndSiDoAndSiGunGuOrderByIdDesc(categoryTitle, siDo, siGunGu);
    }
    public List<Post> findPostsByCategoryAndSidoAndSigunGuAndEupmyeondong(String categoryTitle, String siDo, String siGunGu, String eupMyeonDong){
        return postRepository.findPostsByCategoryTitleAndSiDoAndSiGunGuAndEupMyeonDongOrderByIdDesc(categoryTitle, siDo, siGunGu, eupMyeonDong);
    }
    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public List<Post> findFivePostsByCategory(String categoryTitle){
        return postRepository.findFirst5ByCategoryTitleOrderByIdDesc(categoryTitle);
    }
}

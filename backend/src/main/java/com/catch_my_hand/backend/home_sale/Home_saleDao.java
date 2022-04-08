package com.catch_my_hand.backend.home_sale;

import com.catch_my_hand.backend.home_sale.model.GetPetPreviewRes;
import com.catch_my_hand.backend.home_sale.model.PatchPetReq;
import com.catch_my_hand.backend.home_sale.model.PostPetReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class Home_saleDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 분양 등록
    public int createPet(PostPetReq postPetReq) {
        String createPetQuery = "insert into Product(useridx, categoryidx,title,price,content) VALUES (?,?,?,?,?)";
        Object[] createPetParams = new Object[]{postPetReq.getUseridx(), postPetReq.getCategoryidx(), postPetReq.getTitle(), postPetReq.getPrice(), postPetReq.getContent()};
        this.jdbcTemplate.update(createPetQuery, createPetParams);
        String lastInsertIdQuery = "select last_insert_id()";
        int petIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        return petIdx;
    }

    // 분양 수정
    public int modifyPet(int petidx, PostPetReq postPetReq) {
        String modifyPetQuery = "update Product set categoryidx=?, title=?, price=?, content=? where productidx=?";
        Object[] modifyPetParams = new Object[]{postPetReq.getCategoryidx(), postPetReq.getTitle(), postPetReq.getPrice(), postPetReq.getContent(), petidx};
        return this.jdbcTemplate.update(modifyPetQuery, modifyPetParams);
    }

    // 분양 상태 수정
    public int modifyPetStatus(PatchPetReq patchPetReq) {
        String modifyPetQuery = "update Product set status=? where productidx=?";
        Object[] modifyPetParams = new Object[]{patchPetReq.getStatus(), patchPetReq.getProductidx()};
        return this.jdbcTemplate.update(modifyPetQuery, modifyPetParams);
    }

    // 분양 구매자 수정
    public int modifyPetBuyer(PatchPetReq petReq) {
        String modifyPetQuery = "update Product set buyer=? where productidx=?";
        Object[] modifyPetParams = new Object[]{petReq.getBuyer(), petReq.getProductidx()};
        return this.jdbcTemplate.update(modifyPetQuery, modifyPetParams);
    }

    //분양 강아지 게시물 전체 조회
    public List<GetPetPreviewRes> getPetPreviewRes(int page, int size) {
        String getPetsQuery = "select P.productidx, P.title, P.status, P.price" +
                "from Product P join User U" +
                "on P.useridx = U.useridx" +
                "limit ?, ?";
        Object[] getPetsParams = new Object[]{(page - 1) * size, size};

        return this.jdbcTemplate.query(getPetsQuery,
                (rs, rowNum) -> new GetPetPreviewRes(
                        rs.getInt("productidx"),
                        null,
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("price")), getPetsParams);
    }

    // 현재 분양 진행중인 게시물만 보기
    public List<GetPetPreviewRes> getActivePetList(int page, int size) {
        String getPetQuery = "select P.productidx, P.title, P.status,P.price" +
                "from Product P join User U" +
                "on P.useridx"
    }


    // 상품삭제
    public int deletePet (int petidx) {
        String deleteUserQuery = "delete from Product where productidx =?";
        int getPetParams = petidx;
        return this.jdbcTemplate.update(deleteUserQuery, getPetParams);
    }


}

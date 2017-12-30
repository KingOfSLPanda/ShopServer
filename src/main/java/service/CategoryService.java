package main.java.service;

import main.java.model.Category;
import main.java.repository.CategoryRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25.11.2017.
 */
public class CategoryService {

    public static boolean addCategory(Category category) throws SQLException {
        return (new CategoryRepository()).addCategory(category);
    }

    public static boolean updateCategory(Category category) throws SQLException {
        return  (new CategoryRepository()).updateCategory(category);
    }

    public static boolean deleteCategory(int id) throws SQLException {
        if(ProductService.getProductsByCategoryId(id).size() == 0) {
            return (new CategoryRepository()).deleteCategory(id);
        }
        else return false;
    }

    public static JSONObject convertListOfCategoriesToJSONobject(List<Category> categories) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("sizeCategory", categories.size());
        for (int i=0; i<categories.size(); i++){
            object.put("idCategory" + String.valueOf(i), categories.get(i).getId());
            object.put("categoryNameCategory" + String.valueOf(i), categories.get(i).getCategoryName());
            object.put("numberOfOrdersCategory" + String.valueOf(i), categories.get(i).getNumberOfOrders());
        }
        return object;
    }

    public  static List<Category> getCatigories() throws SQLException {
        List<Category> categories = (new CategoryRepository()).getAllCatigories();
        return  categories;
    }
    public static List<Category> getListOfCategoriesFromJSONobject(String string) throws JSONException {
        JSONObject object = new JSONObject(string);
        List<Category> categories = new ArrayList<Category>();
        for(int i = 0; i < (int)object.get("sizeCategory"); i++){
            Category category = new Category();
            category.setId((int) object.get("idCategory" + String.valueOf(i)));
            category.setCategoryName((String) object.get("categoryNameCategory" + String.valueOf(i)));
            category.setNumberOfOrders((int) object.get("numberOfOrdersCategory" + String.valueOf(i)));
            categories.add(category);
        }
        return  categories;
    }


}

package site.nomoreparties.stellarburgers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String[] ingredients;

    public void setCorrectIngredientsList() {
        this.ingredients = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa73"};
    }

    public void setEmptyIngredientsList() {
        this.ingredients = null;
    }

    public void setIncorrectIngredientsList() {
        this.ingredients = new String[]{"123", "undefined", "Hello"};
    }
}

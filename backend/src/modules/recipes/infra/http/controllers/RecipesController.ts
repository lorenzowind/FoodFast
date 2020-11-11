import { Request, Response } from 'express';
import { container } from 'tsyringe';

import CreateRecipeService from '@modules/recipes/services/CreateRecipeService';
import UpdateRecipeService from '@modules/recipes/services/UpdateRecipeService';
import DeleteRecipeService from '@modules/recipes/services/DeleteRecipeService';
import ListFilteredRecipesService from '@modules/recipes/services/ListFilteredRecipesService';

export default class RecipesController {
  public async create(request: Request, response: Response): Promise<Response> {
    const {
      category_id,
      name,
      description,
      ingredients,
      steps,
      video_url,
    } = request.body;

    const createRecipe = container.resolve(CreateRecipeService);

    const recipe = await createRecipe.execute({
      category_id,
      name,
      description,
      ingredients,
      steps,
      video_url,
    });

    return response.json(recipe);
  }

  public async update(request: Request, response: Response): Promise<Response> {
    const { id } = request.params;
    const {
      category_id,
      name,
      description,
      ingredients,
      steps,
      video_url,
    } = request.body;

    const updateRecipe = container.resolve(UpdateRecipeService);

    const recipe = await updateRecipe.execute({
      id,
      category_id,
      name,
      description,
      ingredients,
      steps,
      video_url,
    });

    return response.json(recipe);
  }

  public async delete(request: Request, response: Response): Promise<Response> {
    const { id } = request.params;

    const deleteRecipe = container.resolve(DeleteRecipeService);

    await deleteRecipe.execute(id);

    return response.status(200).send();
  }

  public async show(request: Request, response: Response): Promise<Response> {
    const user_id = request.user.id;
    const { category_id } = request.params;
    const { search = '', page = 1 } = request.query;

    const listRecipes = container.resolve(ListFilteredRecipesService);

    const recipes = await listRecipes.execute(
      String(search),
      Number(page),
      category_id,
      user_id,
    );

    return response.json(recipes);
  }
}

import { Request, Response } from 'express';
import { container } from 'tsyringe';

import UpdateRecipeImageService from '@modules/recipes/services/UpdateRecipeImageService';

export default class RecipeImageController {
  public async update(request: Request, response: Response): Promise<Response> {
    const { id } = request.params;

    const updateRecipeImage = container.resolve(UpdateRecipeImageService);

    const recipe = await updateRecipeImage.execute({
      recipe_id: id,
      imageFilename: request.file.filename,
    });

    return response.json(recipe);
  }
}

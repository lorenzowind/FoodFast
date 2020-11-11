import { injectable, inject } from 'tsyringe';

import AppError from '@shared/errors/AppError';

import IStorageProvider from '@shared/container/providers/StorageProvider/models/IStorageProvider';

import IRecipesRepository from '../repositories/IRecipesRepository';

import Recipe from '../infra/typeorm/entities/Recipe';

interface IRequest {
  recipe_id: string;
  imageFilename: string;
}

@injectable()
class UpdateRecipeImageService {
  constructor(
    @inject('RecipesRepository')
    private recipesRepository: IRecipesRepository,

    @inject('StorageProvider')
    private storageProvider: IStorageProvider,
  ) {}

  public async execute({
    recipe_id,
    imageFilename,
  }: IRequest): Promise<Recipe> {
    const recipe = await this.recipesRepository.findById(recipe_id);

    if (!recipe) {
      throw new AppError('Recipe not found.');
    }

    if (recipe.image) {
      await this.storageProvider.deleteFile(recipe.image);
    }

    const fileName = await this.storageProvider.saveFile(imageFilename);

    recipe.image = fileName;

    await this.recipesRepository.save(recipe);

    return recipe;
  }
}

export default UpdateRecipeImageService;

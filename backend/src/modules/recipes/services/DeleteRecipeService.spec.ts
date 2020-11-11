import AppError from '@shared/errors/AppError';

import FakeCacheProvider from '@shared/container/providers/CacheProvider/fakes/FakeCacheProvider';

import FakeCategoriesRepository from '@modules/categories/repositories/fakes/FakeCategoriesRepository';
import FakeRecipesRepository from '../repositories/fakes/FakeRecipesRepository';

import DeleteRecipeService from './DeleteRecipeService';

let fakeCacheProvider: FakeCacheProvider;

let fakeCategoriesRepository: FakeCategoriesRepository;
let fakeRecipesRepository: FakeRecipesRepository;

let deleteRecipe: DeleteRecipeService;

describe('DeleteRecipe', () => {
  beforeEach(() => {
    fakeCategoriesRepository = new FakeCategoriesRepository();
    fakeRecipesRepository = new FakeRecipesRepository();

    fakeCacheProvider = new FakeCacheProvider();

    deleteRecipe = new DeleteRecipeService(
      fakeRecipesRepository,
      fakeCacheProvider,
    );
  });

  it('should not be able to delete a non existing recipe', async () => {
    await expect(
      deleteRecipe.execute('Non existing recipe id'),
    ).rejects.toBeInstanceOf(AppError);
  });

  it('should be able to delete a recipe', async () => {
    const category = await fakeCategoriesRepository.create({
      name: 'Category 01',
    });

    const recipe = await fakeRecipesRepository.create({
      category_id: category.id,
      name: 'Recipe 01',
      description: 'Recipe description',
      steps: 'Recipe steps',
      ingredients: 'Recipe ingredients',
      video_url: 'Recipe video URL',
    });

    await deleteRecipe.execute(recipe.id);

    expect(await fakeRecipesRepository.findById(recipe.id)).toBe(undefined);
  });
});

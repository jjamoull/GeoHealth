import { provideTransloco } from '@jsverse/transloco';
import {TranslocoHttpLoader} from './transloco.loader';

export function provideTranslocoConfig() {
  return provideTransloco({
    config: {
      availableLangs: ['fr', 'en'],
      defaultLang: 'fr',
      fallbackLang: 'fr',
      reRenderOnLangChange: true,
      prodMode: false
    },
    loader: TranslocoHttpLoader
  });
}

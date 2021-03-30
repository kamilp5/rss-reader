import DateTimeFormat = Intl.DateTimeFormat;

export class RssItem{
  id: number;
  title: string;
  url: string;
  imageUrl: string;
  description: string;
  date: DateTimeFormat;
  alreadySeen: boolean;
}
